$("#meeting_start").click(function(){
	const meeting_name=$("#meeting_name").val();
	const meeting_date=$("#meeting_date").val();
	const meeting_party=$("#meeting_party").val();
	const meeting_context=$("#meeting_context").val();
	const meeting_ref=$("#meeting_ref").val();
	alert(meeting_name);
	$.post('startMeeting',
		{meeting_name,
		meeting_date,
		meeting_party,
		meeting_context,
		meeting_ref},
		function(data){
			alert(data);
		}
		
	);
});