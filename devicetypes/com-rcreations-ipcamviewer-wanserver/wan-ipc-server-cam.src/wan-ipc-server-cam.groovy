/**
 * connect to ip camera using IP Cam Viewer Embedded Web Server
 * via WAN IP / Port
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
		input("username",	"text",		title: "Viewer username",	description: "Viewer username for embedded web server.", autoCorrect:false)
		input("password",	"password",	title: "Viewer password",	description: "Viewer password for embedded web server.", autoCorrect:false)
		input("ip",			"text",		title: "IP or Hostname",	description: "Embedded web server WAN IP address or Hostname (do not include http://)", autoCorrect:false)
		input("port",		"number",	title: "Port",				description: "Embedded web server WAN Port number")
		input("https",		"bool",		title: "HTTPS",				description: "false for HTTP or true for HTTPS.")
		input("cameraName",	"text",		title: "Camera Name",		description: "Camera name in IP Cam Viewer", autoCorrect:false)
}   

metadata {
	// Automatically generated. Make future change here.
	definition (name: "WAN IPC Server Cam", namespace: "com.rcreations.ipcamviewer.wanserver", author: "robert chou") {
		capability "Actuator"
		capability "Switch"
		capability "Image Capture"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		carouselTile("cameraDetails", "device.image", width: 3, height: 2) { }

		// relay on
		standardTile("buttonOn", "device.switch", width: 1, height: 1, canChangeIcon: false) {
			state "off", label: 'On', action: "switch.on", icon: "st.Office.office6-icn", backgroundColor: "#ffffff"
			state "on", label: 'On', action: "switch.on", icon: "st.Office.office6-icn", backgroundColor: "#ffffff"
		}

		// relay off
		standardTile("buttonOff", "device.switch", width: 1, height: 1, canChangeIcon: false) {
			state "off", label: 'Off', action: "switch.off", icon: "st.Office.office6-icn", backgroundColor: "#ffffff"
			state "on", label: 'Off', action: "switch.off", icon: "st.Office.office6-icn", backgroundColor: "#ffffff"
		}

		standardTile("camera", "device.image", width: 1, height: 1, canChangeIcon: false, inactiveLabel: true, canChangeBackground: true) {
		  state "default", label: '', action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF"
		}

		standardTile("take", "device.image", width: 1, height: 1, canChangeIcon: false, inactiveLabel: true, canChangeBackground: false, decoration: "flat") {
//		  state "take", label: 'Take Photo', action: "Image Capture.take", icon: "st.camera.take-photo", nextState:"taking"          
			state "take", label: "Take Photo", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
			state "taking", label:'Taking', action: "", icon: "st.camera.take-photo", backgroundColor: "#53a7c0"
			state "image", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
          
		}

		main "camera"
		details(["cameraDetails","take","buttonOn","buttonOff"])    
	}
}

// parse events into attributes

def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'image' attribute

}


// handle commands

def parseCameraResponse(def response) {
	log.debug( "parseCameraResponse() started" );
    
	if(response.headers.'Content-Type'.contains("image/jpeg")) {
		def imageBytes = response.data

		if(imageBytes) {
			storeImage(getPictureName(), imageBytes)
		}
	} else {
		log.error("${device.label} could not capture an image.")
	}
}

def getPictureName() {
	log.debug( "getPictureName() started" );
    
	def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
	"image" + "_$pictureUuid" + ".jpg"
}

def take() {
	log.debug( "take() started" );
    
	log.debug("${device.label} taking photo")

	def strUrl = ("true".equals(https)?"https":"http") + "://${username}:${password}@${ip}:${port}/v1/stream/last_frame.jpg?name=${cameraName}";
    log.debug( "strUrl = ${strUrl}" );
    
	httpGet( strUrl ){
		response -> log.info("${device.label} image captured")
		parseCameraResponse(response)
	}
}


// handle motion notification status

def on() {
	log.debug "Relay On"
    
    sendEvent(name: "switch", value: "on")
    
    serverGet( "/v1/cgi/getset.cgi?action=set&key=camera_control.relay&name=${cameraName}&value=1" )
}

def off() {
	log.debug "Relay Off"
    
    sendEvent(name: "switch", value: "off");
    
    serverGet( "/v1/cgi/getset.cgi?action=set&key=camera_control.relay&name=${cameraName}&value=0" )
}

private serverGet(def apiCommand) {

	def strUrl = ("true".equals(https)?"https":"http") + "://${username}:${password}@${ip}:${port}${apiCommand}";
    log.debug( "strUrl = ${strUrl}" );
    
	httpGet( strUrl ){
		response -> log.info("${device.label} apiCommand executed")
//		parseCameraResponse(response)
	}
}