//체크박스
function selectAll(selectAll) {
	const checkboxes = document.getElementsByName('checkAll');

	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}

function checkSelectAll() {
	const checkboxes = document.querySelectorAll('input[name="checkAll"]');
	// 선택된 체크박스
	const checked = document.querySelectorAll('input[name="checkAll"]:checked');
	// select all 체크박스
	const selectAll = document.querySelector('input[name="selectall"]');

	if (checkboxes.length === checked.length) {//전체 체크박스(checkboxes)와 선택된 체크박스(checked)의 갯수를 비교
		selectAll.checked = true; //갯수가 같으면 selectall 체크박스가 선택
	} else {
		selectAll.checked = false;//갯수가 같지 않으면 selectall 체크박스(selectAll)가 선택해제
	}

}

//영구 삭제
function deleteFile() {
	var count = 0;
	var meetingId = [];
	var folderId = [];

	$("input[name='checkAll']:checked").each(function() {
		count++;
	});
	$("input[class='folder']:checked").each(function() {
		folderId.push($(this).val());
	});
	$("input[class='meeting']:checked").each(function() {
		meetingId.push($(this).val());
	});

	//alert("회의록: "+meetingId+"\n폴더: "+folderId);


	if (count < 1) {
		alert("삭제할 파일을 선택해 주세요.");
	} else {
		var redo = confirm(count + "개의 항목을 영구 삭제 하시겠습니까?");

		if (redo == true) {
			$.ajax({
				url: 'wasteBasket/deleteWB',
				type: 'POST',
				data: {
					"meetingId": meetingId,
					"folderId": folderId
				},
				dataType: "text",
				traditional: true,
				success: function(data) {
					alert("삭제 되었습니다.");
					location.reload(true);
				},
				error: function(request, error) {
					alert("fail");
					console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
				}
			});

		} else {
			alert("삭제가 취소 되었습니다.");
		}

	}//count(if-else)

}

//복구
function restoreFile() {
	var count = 0;
	var meetingId = [];
	var folderId = [];

	$("input[name='checkAll']:checked").each(function() {
		count++;
	});
	$("input[class='folder']:checked").each(function() {
		folderId.push($(this).val());
	});
	$("input[class='meeting']:checked").each(function() {
		meetingId.push($(this).val());
	});

	//alert("회의록: " + meetingId + "\n폴더: " + folderId);


	if (count < 1) {
		alert("복구할 파일을 선택해 주세요.");
	} else {
		var redo = confirm(count + "개의 항목을 복구 하시겠습니까?");

		if (redo == true) {
			$.ajax({
				url: 'wasteBasket/redoWB',
				type: 'POST',
				data: {
					"meetingId": meetingId,
					"folderId": folderId
				},
				dataType: "text",
				traditional: true,
				success: function(data) {
					alert("복구 되었습니다.");
					location.href = 'folderList';
				},
				error: function(request, error) {
					alert("fail");
					console.log("code:" + request.status + "\n" +  "message:" + request.responseText + "\n" + "error:" + error);
				}
			});

		} else {
			alert("복구가 취소 되었습니다.");
		}

	}//count(if-else)

}

window.addEventListener("keyup", e => {
    if(modal.style.display === "flex" && e.key === "Escape") {
        modal.style.display = "none";
    }
});
function sendMeetingId(id){
   $('#MoaModal .modal-content').load("wasteBasket/views?sendMeetingId="+id);
   $('#MoaModal').modal();
}

function sendFolderId(id,name){
//alert("id---->"+id+"\nname---->"+name);
   $('#MoaModal2 .modal-content').load("wasteBasket/folderView?sendFolderId="+id+"&sendFolderName="+name);
   $('#MoaModal2').modal();
}
