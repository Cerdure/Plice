$(function () {

  $(".modify-btn").click(function(){
    selectForm = $(this).parent();
    selectMemberId = selectForm.find("#member-id").val();
    $(".mod-alert .data").text("회원 관리 ID : " + selectMemberId);
    $(".modal-background").fadeIn(100);
    $(".mod-alert").fadeIn(300);
  }); 

  $(".delete-btn").click(function(){
    selectForm = $(this).parent();
    selectMemberId = selectForm.find("#member-id").val();
    $(".del-alert .data").text("회원 관리 ID : " + selectMemberId);
    $(".modal-background").fadeIn(100);
    $(".del-alert").fadeIn(300);
  }); 

});

let selectForm;
let selectMemberId;

function hideAlert(_this){
  $(".alert-window").hide();
  $(".modal-background").hide();
  $(".alert-window .data").text("");
}

function memberModify(){
  selectForm.find("#member-id").attr("disabled", false);
  selectForm.submit();
}

function memberDelete(){
  location.href="/admin/member-del?id=" + selectMemberId;
}
