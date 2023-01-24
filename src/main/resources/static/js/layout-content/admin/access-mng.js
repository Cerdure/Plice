function hideAlert() {
  $("option:selected").prop("selected", false);
  $(".alert-window").hide();
  $(".modal-background").hide();
  $(".alert-window .data").text("");
}