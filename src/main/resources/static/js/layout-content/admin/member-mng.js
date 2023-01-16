$(function () {

  $(".modify-btn").click(function(){
    selectForm = $(this).parent();
    selectMemberId = selectForm.find("#id").val();
    $(".mod-alert .data").text("회원 관리 ID : " + selectMemberId);
    $(".modal-background").fadeIn(100);
    $(".mod-alert").fadeIn(300);
  }); 

  $(".delete-btn").click(function(){
    selectMemberId = $(this).closest(".user-info").find(".id").val();
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

function memberModify(_this){
  selectForm.submit();
}
