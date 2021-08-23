$(document).ready(function(){
var temp = '';
var repeatflag = false;

timer = setInterval(function() {
	$.ajax({
		url: "infiniteReq",
		type: "get",
		success: function(html) {
			if (repeatflag == false) {
				repeatflag = true;
				temp = html.content;
			}

			if (temp != html.content) {
				temp = html.content;

				if (html.length != 0) {
					var htm = '';
					//console.log(html.content);

					htm += '<tr>';
					htm += '<td>' + html.time + '\t' + '</td>';
					htm += '<td>' + 'speaker' + html.speaker + '</td>';
					htm += '<td>' + html.content + '</td>';

					htm += '</tr>';
					htm += '<tr>';
					htm += '<td colspan="3"><input  type="text" style="height:5px; width:2000px; text-align:left;"></textarea></td>';
					htm += '</tr>';

					$("#speakTable").append(htm);

				}
			}
		}
	});
}, 500);


$(document).on("click", "#record_end2", function() {
	/*alert('record_end2');*/
	var memo;
	var dataArrayToSend = [];

	$("#speakTable tr").each(function() {
		var len = $(this).find("td").length;
		if (len == 1) {
			var memostr = $(this).find("td").eq(0).find('input[type="text"]').val();
			if (memostr.length != 0) {
				memo = { memo: memostr };
				dataArrayToSend.push(memo);
			}
		}
	});

	var list = { list: dataArrayToSend };

	var formObj = $("form[name='actionForm']");

	$("#inputHidden").attr("value", JSON.stringify(list));
	formObj.attr("method", "post");
	formObj.submit();


});








//webkitURL is deprecated but nevertheless
URL = window.URL || window.webkitURL;

var gumStream; 						//stream from getUserMedia()
var rec; 							//Recorder.js object
var input; 							//MediaStreamAudioSourceNode we'll be recording

// shim for AudioContext when it's not avb. 
var AudioContext = window.AudioContext || window.webkitAudioContext;
var audioContext = new AudioContext(); //audio context to help us record
var chkHearMic = document.getElementById("chk-hear-mic")

var recordButton = document.getElementById("record_start");
var stopButton = document.getElementById("record_end0");
var pauseButton = document.getElementById("record_pause");
var resumeButton = document.getElementById("record_restart");


const analyser = audioContext.createAnalyser()
function makeSound(stream) {
	const source = audioContext.createMediaStreamSource(stream)
	source.connect(analyser)
	analyser.connect(audioContext.destination)
    }
if (navigator.mediaDevices) {
	console.log('getUserMedia supported.')
	const constraints = {
		audio: true
	}
	navigator.mediaDevices.getUserMedia(constraints)
		.then(stream => {
			chkHearMic.onchange = e => {
				if(e.target.checked == true) {
					audioContext.resume()
					makeSound(stream)
				} else {
					audioContext.suspend()
				}
			}
		})
	}
                

//add events to those 2 buttons
recordButton.addEventListener("click", startRecording);
stopButton.addEventListener("click", stopRecording);
pauseButton.addEventListener("click", pauseRecording);
resumeButton.addEventListener("click", resumeRecording);

function startRecording() {
	console.log("recordButton clicked");

	/*
		Simple constraints object, for more advanced audio features see
		https://addpipe.com/blog/audio-constraints-getusermedia/
	*/
    
    var constraints = { audio: true, video:false }

 	/*
    	Disable the record button until we get a success or fail from getUserMedia() 
	*/

	recordButton.disabled = true;
	stopButton.disabled = false;
	pauseButton.disabled = false

	/*
    	We're using the standard promise based getUserMedia() 
    	https://developer.mozilla.org/en-US/docs/Web/API/MediaDevices/getUserMedia
	*/
	
	$("#record_start").val("Recording");
	$("#record_start").attr("disabled", "disabled");
	$("#record_start").css({ background: "#ff6666", color: "#ffffff" });
	$("#record_pause").attr("disabled", false);
	$("#record_pause").css({ background: "#56BAED", color: "#ffffff" });
	$("#record_end0").attr("disabled", false);
	$("#record_end0").css({ background: "#56BAED", color: "#ffffff" });
	$.ajax({
		url: "startRecording",
		type: "post",
		success: function(html) {
		}
	});
	
	
	navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
		console.log("getUserMedia() success, stream created, initializing Recorder.js ...");

		/*
			create an audio context after getUserMedia is called
			sampleRate might change after getUserMedia is called, like it does on macOS when recording through AirPods
			the sampleRate defaults to the one set in your OS for your playback device
		*/
		/*audioContext = new AudioContext();*/

		//update the format 
		document.getElementById("formats").innerHTML="Format: 1 channel pcm @ "+audioContext.sampleRate/1000+"kHz"

		/*  assign to gumStream for later use  */
		gumStream = stream;
		
		/* use the stream */
		input = audioContext.createMediaStreamSource(stream);

		/* 
			Create the Recorder object and configure to record mono sound (1 channel)
			Recording 2 channels  will double the file size
		*/
		rec = new Recorder(input,{numChannels:1})

		//start the recording process
		rec.record()

		console.log("Recording started");

	}).catch(function() {
	  	//enable the record button if getUserMedia() fails
    	recordButton.disabled = false;
    	stopButton.disabled = true;
    	pauseButton.disabled = true
	});
	
	
	
	
}

function pauseRecording(){
	console.log("pauseButton clicked rec.recording=",rec.recording );

		rec.stop();
		
		pauseButton.disabled = true;
		resumeButton.disabled = false;
		
	$.ajax({
		url: "pauseRecording",
		type: "post",
		success: function() {
			$("#record_pause").attr("disabled", true);
			$("#record_pause").css({ background: "#ff6666", color: "#ffffff" });
			$("#record_restart").attr("disabled", false);
			$("#record_restart").css({ background: "#56BAED", color: "#ffffff" });
		}
	});
}

function resumeRecording(){
	console.log("pauseButton clicked rec.recording=",rec.recording );
	
		rec.record()
		
		pauseButton.disabled = false;
		resumeButton.disabled = true;
		
		$.ajax({
		url: "restartRecording",
		type: "post",
		success: function() {
			$("#record_pause").attr("disabled", false);
			$("#record_pause").css({ background: "#56BAED", color: "#ffffff" });
			$("#record_restart").attr("disabled", true);
			$("#record_restart").css({ background: "#ff6666", color: "#ffffff" });
		}
	});
}



function stopRecording() {
	console.log("stopButton clicked");

	//disable the stop button, enable the record too allow for new recordings
	stopButton.disabled = true;
	recordButton.disabled = false;
	pauseButton.disabled = true;
	resumeButton.disabled = true;

	//reset button just in case the recording is stopped while paused
	//pauseButton.innerHTML="Pause";
	
	//tell the recorder to stop the recording
	rec.stop();

	//stop microphone access
	gumStream.getAudioTracks()[0].stop();

	//create the wav blob and pass it on to createDownloadLink
	rec.exportWAV(createDownloadLink);
	
	/*alert('record_end0');*/
	$("#record_end0").attr("hidden", true);
	var bodyHtml = '<input type="button" name="record_end2" id="record_end2" value="Confirm" style="background-color: #56BAED onclick="record_end">';
	$("#mainDiv").append(bodyHtml);
}

function createDownloadLink(blob) {
	
	var url = URL.createObjectURL(blob);
	var au = document.createElement('audio');
	var li = document.createElement('li');
	var link = document.createElement('a');

	//name of .wav file to use during upload and download (without extendion)
	var filename = new Date().toISOString();

	//add controls to the <audio> element
	au.controls = true;
	au.src = url;

	//save to disk link
	link.href = url;
	link.download = filename+".wav"; //download forces the browser to donwload the file using the  filename
	link.innerHTML = "Save to disk";
    console.log(link);

	//add the new audio element to li
	li.appendChild(au);
	
	//add the filename to the li
	li.appendChild(document.createTextNode(filename+".wav "))

	//add the save to disk link to li
	li.appendChild(link);
	
	//upload link
	var upload = document.createElement('a');
	upload.href="#";
	//upload.innerHTML = "Upload";	//업로드 필요없어서 비활성화
	upload.addEventListener("click", function(event){
		  var xhr=new XMLHttpRequest();
		  xhr.onload=function(e) {
		      if(this.readyState === 4) {
		          console.log("Server returned: ",e.target.responseText);
		      }
		  };
		  var fd=new FormData();
		  fd.append("audio_data",blob, filename);
		  xhr.open("POST","upload.php",true);
		  xhr.send(fd);
	})
	li.appendChild(document.createTextNode (" "))//add a space in between
	li.appendChild(upload)//add the upload link to li

	//add the li element to the ol
	recordingsList.appendChild(li);
}



});