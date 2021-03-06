/**
 *  Life360 Presence Sensor
 *
 *  Copyright 2018 Michael Barksdale
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
metadata {
	definition (name: "Life360 Presence Sensor", namespace: "ABETOWNCARR", author: "Michael Barksdale") {
		capability "Acceleration Sensor"
		capability "Audio Notification"
		capability "Estimated Time Of Arrival"
		capability "Geolocation"
		capability "Location Mode"
		capability "Polling"
		capability "Presence Sensor"
		
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		// TODO: define your main and details tiles here
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'acceleration' attribute
	// TODO: handle 'eta' attribute
	// TODO: handle 'latitude' attribute
	// TODO: handle 'longitude' attribute
	// TODO: handle 'method' attribute
	// TODO: handle 'accuracy' attribute
	// TODO: handle 'altitudeAccuracy' attribute
	// TODO: handle 'heading' attribute
	// TODO: handle 'speed' attribute
	// TODO: handle 'lastUpdateTime' attribute
	// TODO: handle 'mode' attribute
	// TODO: handle 'presence' attribute
	// TODO: handle 'shock' attribute

}

// handle commands
def playTrack() {
	log.debug "Executing 'playTrack'"
	// TODO: handle 'playTrack' command
}

def playTrackAndResume() {
	log.debug "Executing 'playTrackAndResume'"
	// TODO: handle 'playTrackAndResume' command
}

def playTrackAndRestore() {
	log.debug "Executing 'playTrackAndRestore'"
	// TODO: handle 'playTrackAndRestore' command
}

def setMode() {
	log.debug "Executing 'setMode'"
	// TODO: handle 'setMode' command
}

def poll() {
	log.debug "Executing 'poll'"
	// TODO: handle 'poll' command
}