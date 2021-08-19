//스크롤
$(".messages").animate({ scrollTop: $(document).height() }, "fast");

//챗봇 열기
$("#chat-circle").click(function() {
	$("#chat-circle").toggle('scale');
	$(".chat-box").toggle('scale');
});

//챗봇 닫기 
$(".chat-box-toggle").click(function() {
	$("#chat-circle").toggle('scale');
	$(".chat-box").toggle('scale');
});

//엔터시 newMessage() 호출
$(window).on('keydown', function(e) {
	if (e.which == 13) { // Enter key: 13
		newMessage();
		return false;
	}
});

//챗봇 주고 받기
$('.chat-submit').click(function() {
	newMessage();
});
function newMessage() {
	var message = $(".chat-input input").val();

	if ($.trim(message) == '') {
		return false;
	}
	$('<li class="replies"><p>' + message + '</p></li>').appendTo($('.messages ul'));
	$('.chat-input input').val(null);
	$(".messages").animate({ scrollTop: $(document).height() }, "fast");

	$.ajax({
		url: 'sendMsg',
		type: 'POST',
		data: {
			"message": message
		},
		dataType: "text",
		traditional: true,
		success: function(data) {
			if (message.indexOf("#") != -1) {
				findMessage(message.replace("#", ""));
			}else{
				$('<li class="sent"><p> ' + data + '</p></li>').appendTo($('.messages ul'));
			$(".messages").animate({ scrollTop: $(document).height() }, "fast");
			}
			
		},
		error: function(request, error) {
			alert("fail");
			console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}
//검색
function findMessage(searchText) {
	$('<li class="sent"><p>" ' + searchText + ' "를 검색합니다. 잠시 기다려주세요! </p></li>').appendTo($('.messages ul'));
	setTimeout(function() {
		location.href = "search?searchText=" + searchText;
	}, 500);

}

//vonoFAQ 클릭시 FAQ 나옴
var msg1 = "VONO가 뭐야";
var msg2 = "VONOBOT이 뭐야";
var msg3 = "누가 만들었어?";
$('#vonoFAQ').click(function() {
	$('<div class="dropdown"><li class="sent">'
		+ '<button type="button" class="btn btn-primary dropdown toggle" data-toggle="dropdown">FAQ <i class="fas fa-caret-down"></i></button>'
		+ '<ol class="dropdown-menu" style="width: 200px;" role="menu">'
		+ '<li><a onclick="qna(1)" class="dropdown-item"><span class="far fa-lightbulb"> ' + msg1 + '</span></a></li>'
		+ '<li><a onclick="qna(2)" class="dropdown-item" href="#"><span class="far fa-lightbulb"> ' + msg2 + '</span></a></li>'
		+ '<li><a onclick="qna(3)" class="dropdown-item" href="#"><span class="far fa-lightbulb"> ' + msg3 + '</span></a></li>'
		+ '</li></div>').appendTo($('.messages ul'));

	$(".messages").animate({ scrollTop: $(document).height() }, "fast");
});
function qna(str) {
	var message = "";
	if (str == 1) {
		$('<li class="replies"><p> ' + msg1 + '</p></li>').appendTo($('.messages ul'));
		message += msg1;

	} else if (str == 2) {
		$('<li class="replies"><p> ' + msg2 + '</p></li>').appendTo($('.messages ul')); message += msg2;
	} else if (str == 3) {
		$('<li class="replies"><p> ' + msg3 + '</p></li>').appendTo($('.messages ul')); message += msg3;
	} $(".messages").animate({ scrollTop: $(document).height() }, "fast");

	$.ajax({
		url: 'sendMsg',
		type: 'POST',
		data: {
			"message": message
		},
		dataType: "text",
		traditional: true,
		success: function(data) {
			$('<li class="sent"><p> ' + data + '</p></li>').appendTo($('.messages ul'));
			$(".messages").animate({ scrollTop: $(document).height() }, "fast");
		},
		error: function(request, error) {
			alert("fail");
			console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}
