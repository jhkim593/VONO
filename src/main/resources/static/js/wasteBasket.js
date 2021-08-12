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
	var fileId = [];

	$("input[name='checkAll']:checked").each(function(i) {
		count++;
		fileId.push($(this).val());
	});
	console.log("fileName: " + fileId);

	if (count < 1) {
		alert("삭제할 파일을 선택해 주세요.");
	} else {
		var redo = confirm(count + "개의 항목을 영구 삭제 하시겠습니까?");

		if (redo == true) {
			//alert(fileId);
			alert("삭제 되었습니다.");

			$.ajax({
				url: 'deleteWB',
				data: "id="+fileId,
				type: 'POST',
				success: function onData(data) {
					console.log("data: "+data);
					location.reload(true);
				},
				error: function onError(error) {
					console.error("error: "+error);
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
	var fileId = [];

	$("input[name='checkAll']:checked").each(function(i) {
		count++;
		fileId.push($(this).val());
	});
	console.log("fileName: " + fileId);

	if (count < 1) {
		alert("복구할 파일을 선택해 주세요.");
	} else {
		var redo = confirm(count + "개의 항목을 복구 하시겠습니까?");

		if (redo == true) {
			//alert(fileId);
			alert("복구 되었습니다.");

			$.ajax({
				url: 'redoWB',
				data: "id="+fileId,
				type: 'POST',
				success: function onData(data) {
					console.log("data: "+data);
					location.href="wasteBasket";
				},
				error: function onError(error) {
					console.error("error: "+error);
				}
			});
			
		} else {
			alert("복구가 취소 되었습니다.");
		}
	}//count(if-else)

}










