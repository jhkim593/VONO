$("#getFileList").click(function(){
		const id = $("#getFileList").attr("th:id");
		consol.log(id);
/*					$.post(
						"/getFileList",
						{id},
						function(data){
							data= JSON.parse(data);
							alert(data.msg);
							
							location.reload();
					});*/
			});	