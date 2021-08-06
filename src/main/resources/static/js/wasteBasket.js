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
	var fileName = [];

	$("input[name='checkAll']:checked").each(function(i) {
		count++;
		fileName.push($(this).val());
	});
	console.log("fileName: " + fileName);

	if (count < 1) {
		alert("삭제할 파일을 선택해 주세요.");
	} else {
		var redo = confirm(count + "개의 항목을 영구 삭제 하시겠습니까?");


		if (redo == true) {
			alert(fileName);
			//alert("삭제 되었습니다.");

			/*$.post("deleteWB",
				{
					fileName
				},
				function(data, status) {
					alert("data: " + data + "\n status: " + status);
				});
*/



			$.ajax({
				url: 'deleteWB',
				data: fileName,
				type: 'POST',
				success: function onData(data) {
					console.log("data: "+data);
				},
				error: function onError(error) {
					console.error("error: "+error);
				}
			});




			//window.location.href="wasteBasketDemo";
			//location.reload(true);

		} else {
			alert("삭제가 취소 되었습니다.");
		}
	}

}

//복구
function restoreFile() {
	var count = 0;
	var obj_length = document.getElementsByName("checkAll").length;
	const checked = document.querySelectorAll('input[name="checkAll"]:checked');

	if (checked) {
		for (var i = 0; i < obj_length; i++) {
			if (document.getElementsByName("checkAll")[i].checked == true) {
				count++;
				//파일이름 확인 console.log(document.getElementsByName("checkAll")[i].value);
			}
		}

		if (count < 1) {
			alert("복구할 파일을 선택해 주세요.");
		} else {
			var redo = confirm(count + "개의 항목을 복구 하시겠습니까?");
			if (redo == true) {
				//복구 쿼리
			}
		}

	}

}