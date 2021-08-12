$("#meeting_start").click(function(){
	const name=$("#meeting_name").val();
	const date=$("#meeting_date").val();
	const participant=$("#meeting_party").val();
	const content=$("#meeting_content").val();
	const reference=$("#meeting_ref").val();
	//alert(name);
	$.post('startMeeting',
		{name,
		date,
		participant,
		content,
		reference},
		function(data){
			alert(data);
			//data=JSON.parse(data);
			//alert(data);
		}
		
	);
});