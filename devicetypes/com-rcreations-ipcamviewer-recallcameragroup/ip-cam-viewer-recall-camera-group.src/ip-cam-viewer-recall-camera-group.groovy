/**
 *  IP Cam Viewer Recall Camera Group
 *
 *  Copyright 2014 robert chou
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
preferences {
		input("username",	"text",		title: "Admin username",	description: "Admin username for embedded web server.", autoCorrect:false)
		input("password",	"password",	title: "Admin password",	description: "Admin password for embedded web server.", autoCorrect:false)
		input("ip",			"text",		title: "IP or Hostname",	description: "Embedded web server IP address or Hostname (do not include http://)", autoCorrect:false)
		input("port",		"number",	title: "Port",				description: "Embedded web server Port number")
		input("groupOn",	"text",		title: "Camera Group When On",	description: "Name of camera group to select when switch is turned on.", autoCorrect:false)
		input("groupOff",	"text",		title: "Camera Group When Off",	description: "Name of camera group to select when switch is turned off.", autoCorrect:false)
}   
 
metadata {
	definition (name: "IP Cam Viewer Recall Camera Group", namespace: "com.rcreations.ipcamviewer.RecallCameraGroup", author: "robert chou") {
		capability "Switch"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("buttonOn", "device.switch", width: 1, height: 1, canChangeIcon: false) {
			state "off", label: 'On', action: "switch.on", icon: "st.Entertainment.entertainment9-icn", backgroundColor: "#ffffff"
			state "on", label: 'On', action: "switch.on", icon: "st.Entertainment.entertainment9-icn", backgroundColor: "#ffffff"
		}

		standardTile("buttonOff", "device.switch", width: 1, height: 1, canChangeIcon: false) {
			state "off", label: 'Off', action: "switch.off", icon: "st.Entertainment.entertainment9-icn", backgroundColor: "#ffffff"
			state "on", label: 'Off', action: "switch.off", icon: "st.Entertainment.entertainment9-icn", backgroundColor: "#ffffff"
		}

		main "buttonOn"
		details(["buttonOn","buttonOff"])        
	}
}


//
// implement commands
//

def on() {
	log.debug "Recall 'on' camera group ${groupOn}"
        
    // recall group
	def apiCommand = "/v1/cgi/getset.cgi?action=set&key=camera_db.recall_group&value=${groupOn}";
	doGet( apiCommand, false ) {
		log.debug "Recalled 'on' camera group ${groupOn}"
	}    
}

def off() {
	log.debug "Recall 'off' camera group ${groupOn}"
        
    // recall group
	def apiCommand = "/v1/cgi/getset.cgi?action=set&key=camera_db.recall_group&value=${groupOff}";
	doGet( apiCommand, false ) {
		log.debug "Recalled 'off' camera group ${groupOff}"
	}    
}


//
// lan network calls
//

private getHostAddress() {
	return "${ip}:${port}"
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex

}

private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}

private String appendLoginParams( String apiCommand ) {
	String output;
    if( apiCommand.contains("?") || apiCommand.contains("&") )
    {
    	output = "&";
    }
    else
    {
    	output = "?";
    }
    output += "user=${username}&pass=${password}";
}

private hubGetLan(def apiCommand)
{
	//Setting Network Device Id
    def iphex = convertIPtoHex(ip)
    def porthex = convertPortToHex(port)
    device.deviceNetworkId = "$iphex:$porthex"
    log.debug "Device Network Id set to ${iphex}:${porthex}"

	log.debug("Executing hubaction on " + getHostAddress())
    def uri = apiCommand + appendLoginParams(apiCommand);
    log.debug( "hubAction ${uri}" );
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
        path: uri,
        headers: [HOST:getHostAddress()]
    )
    hubAction
//	def result = hubAction
//    log.debug( "hubAction = ${result}" );
}

// parse hubAction results
def parse(String description) {
	log.debug "Parsing '${description}'"
    
    def map = [:]
    def retResult = []
    def descMap = parseDescriptionAsMap(description)
        
    //Image
	if (descMap["bucket"] && descMap["key"]) {
		putImageInS3(descMap)
	}

	//Status Polling
    else if (descMap["headers"] && descMap["body"]) {
        def body = new String(descMap["body"].decodeBase64())
		log.debug "Parsing body = '${body}'"
        if( body != null )
        {
	        // result of command, but don't know source cmd because device only returns status
    	    if( body.equals("{ result : 1 }") )
        	{
      			// but below recursive call to hubAction via poll() causes problem with NO result ever (parse not called)
                // so caller using lan access will have to set state before device actually finishes
                
                // poll to update status
//              log.debug( "poll to get status" );
//        		poll();
        	}
        }
	}
}

def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
		def nameAndValue = param.split(":")
		map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}

def putImageInS3(map) {

	def s3ObjectContent

	try {
		def imageBytes = getS3Object(map.bucket, map.key + ".jpg")

		if(imageBytes)
		{
			s3ObjectContent = imageBytes.getObjectContent()
			def bytes = new ByteArrayInputStream(s3ObjectContent.bytes)
			storeImage(getPictureName(), bytes)
		}
	}
	catch(Exception e) {
		log.error e
	}
	finally {
		//Explicitly close the stream
		if (s3ObjectContent) { s3ObjectContent.close() }
	}
}

//
// wan network calls
//

private serverGetWan(def apiCommand, success = {})
{
	def strUrl = "http://${username}:${password}@${ip}:${port}${apiCommand}";
    log.debug( "strUrl = ${strUrl}" );
    
	httpGet( strUrl ){
		response -> log.info("${device.label} apiCommand executed")
		success(response)
	}
}

//
// network calls
//

private isLan()
{
   boolean bLan = false;
   if( ip != null )
   {
   		bLan = ip.startsWith("10.") || ip.startsWith("192.");
   }
   return bLan;
}

private doGet(def apiCommand, boolean bResultHasIdentifier, success = {})
{
	boolean bLan = isLan();
	log.debug( "bLan = ${bLan}" );
    
    apiCommand = apiCommand.replaceAll( " ", "%20" );
    
    if( bLan )
    {
    	def result = hubGetLan( apiCommand );
        // if result has an identifier for the source cmd, then parse() can handle it properly
        // but if result has no identifier, then parse() can try to do a poll() but it turns out call hubAction while in parse will cause the second parse() to never get called
        // so for this case, we'll call sucess before result is known...
        if( bResultHasIdentifier == false )
        {
        	success( "{ result : 1 }" );
        }
		return result; // hubAction must be returned
    }
    else
    {
    	serverGetWan( apiCommand, success );
    }
}