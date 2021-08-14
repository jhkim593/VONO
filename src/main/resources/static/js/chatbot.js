$(function() {
  var INDEX = 0; 
  $("#chat-submit").click(function(e) {
    e.preventDefault();
    var msg = $("#chat-input").val(); 
    if(msg.trim() == ''){
      return false;
    }
    generate_message(msg, 'self');
   
     if(msg=='안녕'){
	setTimeout(function() {      
     msg="안녕하세요, 보노봇이에요";
    generate_message(msg, 'user');
    }, 300)
    }
    
    /*$("#vonoFAQ").click(function() {    
    msg="안녕하세요, 보노봇이 알려드릴게요!";
    generate_message(msg, 'user');
  	  })*/
    
    
  })
  
  function generate_message(msg, type) {
    INDEX++;
    var str="";
    str += "<div id='cm-msg-"+INDEX+"' class=\"chat-msg "+type+"\">";
    str += "          <span class=\"msg-avatar\">";
    str += "          <\/span>";
    str += "          <div class=\"cm-msg-text\">";
    str += msg;
    str += "          <\/div>";
    str += "        <\/div>";
    $(".chat-logs").append(str);
    $("#cm-msg-"+INDEX).hide().fadeIn(300);
    //전송 후에 input에 공백처리 해줌
    if(type == 'self'){
     $("#chat-input").val(''); 
    }    
   // $(".chat-logs").stop().animate({ scrollTop: $(".chat-logs")[0].scrollHeight}, 500);    
  }  
  
  
  $(document).delegate(".chat-btn", "click", function() {
    var value = $(this).attr("chat-value");
    var name = $(this).html();
    $("#chat-input").attr("disabled", false);
    generate_message(name, 'self');
    
    /* $("#vonoFAQ").click(function() {    
    msg="안녕하세요, 보노봇이 알려드릴게요!";
    generate_message(msg, 'user');
  	  })*/
  })
  
  $("#chat-circle").click(function() {    
    $("#chat-circle").toggle('scale');
    $(".chat-box").toggle('scale');
  })
  
  $(".chat-box-toggle").click(function() {
    $("#chat-circle").toggle('scale');
    $(".chat-box").toggle('scale');
  })
  

})