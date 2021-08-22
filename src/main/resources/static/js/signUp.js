/**
 * 
 */
window.onload = function() {
	//id와pw 적합여부 검사(4~12자리, 영어대소문자, 숫자만 가능)
	let val = /^[a-zA-Z0-9]{4,15}$/		
	
	//생년월일 적합여부 검사
	let birth_val = /([0-9]{2}(0[1-9]{1}|1[0-2]{1})(0[1-9]{1}|[1,2]{1}[0-9]{1}|3[0,1]{1}))/g
	
	//이메일형식 적합여부 검사
	let mail_val = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
	
	//폰번호 적합여부 검사
	let phone_val = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/
	
	//형식검사하는 메서드
	function check( val, target ) {
		if( val.test( target ) ) {
			return true;
		}
	}
	
	//id 중복 검사
	$( "#user_id" ).blur( function() {
		var user_id = $( "#user_id" ).val();
		if( user_id != '' ) {
			if( check( val, user_id ) ) {
				$.ajax({
					url: './usingId_chk.do?user_id='+user_id,
					type: 'GET',
					success: function( data ) {
						
						if( data == "0" ) {
							$( "#usingId_chk" ).text( "사용할 수 있는 ID입니다." );
							$( "#usingId_chk" ).css( "margin-left", "155px" );
							$( "#usingId_chk" ).css( "color", "blue" );
							$( "#submit1" ).attr( "disabled", false );
						} else if( data == "1"  ) {
							$( "#usingId_chk" ).text( "사용중인 ID입니다." );
							$( "#usingId_chk" ).css( "margin-left", "155px" );
							$( "#usingId_chk" ).css( "color", "red" );
							$( "#submit1" ).attr( "disabled", true );
						}
					},
					error: function() {
						console.log( "signupForm의 ajax 에러" )
					}
				})
			} else {
				$( "#usingId_chk" ).text( "ID가 형식에 맞지않습니다." );
				$( "#usingId_chk" ).css( "margin-left", "155px" );
				$( "#usingId_chk" ).css( "color", "red" );
				$( "#user_id" ).val( "" );
				$( "#submit1" ).attr( "disabled", true );
			}	
		} else {
			$( "#usingId_chk" ).text( "" );
		}
	})

	//패스워드 유효성 검사
	$( "#user_pwd" ).blur( function() {
		var user_pwd = $( "#user_pwd" ).val();
		
		if( !check( val, user_pwd ) ) {
			$( "#pwd_chk" ).text( "패스워드가 형식에 맞지 않습니다." );
			$( "#pwd_chk" ).css( "margin-left", "155px" );
			$( "#pwd_chk" ).css( "color", "red" );
			$( "#user_pwd" ).val( "" );
			$( "#submit1" ).attr( "disabled", true );
		} else{
			$( "#pwd_chk" ).text( "" );
			//패스워드 똑같이 입력했는지 검사
			$( "#user_cpwd" ).blur( function() {
				var user_pwd = $( "#user_pwd" ).val();
				var user_cpwd = $( "#user_cpwd" ).val();
				
				if( user_pwd != '' && user_cpwd != '' ) {
					if( user_pwd == user_cpwd ) {
						$( "#cpwd_chk" ).text( "비밀번호가 같습니다." );
						$( "#cpwd_chk" ).css( "margin-left", "155px" );
						$( "#cpwd_chk" ).css( "color", "blue" );
						$( "#submit1" ).attr( "disabled", false );
					} else {
						$( "#cpwd_chk" ).text( "비밀번호가 틀립니다." );
						$( "#cpwd_chk" ).css( "margin-left", "155px" );
						$( "#cpwd_chk" ).css( "color", "red" );
						$( "#user_cpwd" ).val( "" );
						$( "#submit1" ).attr( "disabled", true );
					}
				} else {
					$( "#pwd_chk" ).text( "" );
					$( "#submit1" ).attr( "disabled", false );
				}
				
			})
		}
	})
	
	//생년월일 유효성검사
	$( "#user_birth" ).blur( function() {
		var user_birth = $( '#user_birth' ).val();
		
		if( user_birth.length != 6 || !check( birth_val, user_birth ) ) {
			$( "#birth_chk" ).text( "생년월일이 형식에 맞지않습니다." );
			$( "#birth_chk" ).css( "margin-left", "155px" );
			$( "#birth_chk" ).css( "color", "red" );
			$( '#user_birth' ).val( "" );
			$( "#submit1" ).attr( "disabled", true );
		} else {
			$( "#birth_chk" ).text( "" );
			$( "#submit1" ).attr( "disabled", false );
		}
	})
	
	//이메일 유효성검사
	$( "#user_mail" ).blur( function() {
		var user_mail = $( "#user_mail" ).val();
		
		if( !check( mail_val, user_mail ) ) {
			$( "#mail_chk" ).text( "메일이 형식에 맞지않습니다." );
			$( "#mail_chk" ).css( "margin-left", "155px" );
			$( "#mail_chk" ).css( "color", "red" );
			$( '#user_mail' ).val( "" );
			$( "#submit1" ).attr( "disabled", true );
		} else {
			$( "#mail_chk" ).text( "" );
			$( "#submit1" ).attr( "disabled", false );
		}
	})
	
	//전화번호 유효성검사
	$( '#user_phone' ).blur( function() {
		var user_phone = $( '#user_phone' ).val();
		
		if( !check( phone_val, user_phone ) ) {
			$( "#phone_chk" ).text( "휴대폰번호가 형식에 맞지않습니다." );
			$( "#phone_chk" ).css( "margin-left", "155px" );
			$( "#phone_chk" ).css( "color", "red" );
			$( '#user_phone' ).val( "" );
			$( "#submit1" ).attr( "disabled", true );
		} else {
			$( "#phone_chk" ).text( "" );
			$( "#submit1" ).attr( "disabled", false );
		}
	})
	
	document.getElementById('submit1').onclick = function() {
		let id = document.sfrm.id.value.trim()
		let pwd = document.sfrm.pwd.value.trim()
		let mail = document.sfrm.mail.value.trim()
		let birth = document.sfrm.birth.value.trim()
		let phone = document.sfrm.phone.value.trim()
		
		if( id == '' ) {
			alert( 'ID를 입력하셔야 합니다.' )
			document.sfrm.id.focus()
			return false;
		} 
		if( pwd == '' ) {
			alert( '비밀번호를 입력하셔야 합니다.')
			return false;
		}
		if( document.sfrm.name.value.trim() == '' ) {
			alert( '이름을 입력하셔야 합니다.')
			return false;
		}
		if( birth == '' ) {
			alert( '생년월일을 입력하셔야 합니다.')
			return false;
		}
		if( mail == '' ) {
			alert( '이메일을 입력하셔야 합니다.')
			return false;
		}
		if( phone == '' ) {
			alert( '핸드폰 번호를 입력하셔야 합니다.')
			return false;
		}
		if( document.sfrm.nick.value.trim() == '' ) {
			alert( '닉네임을 입력하셔야 합니다.')
			return false;
		}
		if(document.sfrm.info.checked == false) {
			alert('동의를 하셔야 합니다.');
			return false;
		}
		document.sfrm.submit();
	};
	
};
