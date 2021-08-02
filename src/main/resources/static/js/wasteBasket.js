
function selectAll(selectAll) {
	const checkboxes
		= document.getElementsByName('checkAll');

	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}

function checkSelectAll() {
	// 전체 체크박스
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

function deleteFile() {
	//alert("해당 파일을 영구 삭제 하시겠습니까?");
	var count = 0;
	var obj_length = document.getElementsByName("checkAll").length;
	const checked = document.querySelectorAll('input[name="checkAll"]:checked');
	
	if(checked){
		for (var i = 0; i < obj_length; i++) {
		if (document.getElementsByName("checkAll")[i].checked == true) {
			count++;
			console.log(document.getElementsByName("checkAll")[i].value);
		}
		
	}
	alert(count+"개의 항목을 영구 삭제 하시겠습니까?");
	}else{
		alert("삭제할 파일을 선택해 주세요.");
	}
}

function restoreFile() {
	alert("해당 파일을 복구 하시겠습니까?");
}