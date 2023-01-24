$(function () {

  memberDetailUpdate($("#admin-1").data("id"));

  $(document).on("click", ".save-btn", o => {
    (async () => {
      const result = await fetch("/admin/admin-mng/update", {
        method: 'post',
        cache: 'no-cache',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: $("#detail-form").serialize()
      }).then(res => {
        alert("저장되었습니다.");
        return res.text();
      });
      $(".accounts-wrapper").replaceWith(result);
    })();
  });

  $(document).on("click", ".delete-btn", o => {
    $(".modal-background").fadeIn(100);
    $(".del-alert").fadeIn(300);
  })

  $(document).on("click", ".team-group .member", e => {
    memberDetailUpdate(e.target.dataset.id);
  });

  $(document).on("click", ".create-team-btn", o => {
    $(".modal-background").fadeIn(100);
    $(".team-create-window").fadeIn(300);
  });

  $(document).on("click", ".create-admin-btn", o => {
    $(".modal-background").fadeIn(100);
    $(".admin-create-window").fadeIn(300);
  });

});

function hideAlert() {
  $(".alert-window").hide();
  $(".modal-background").hide();
  $(".alert-window input").val('');
}

function teamCreate() {
  const teamName = $(".team-create-window input").val();
  fetch("/admin/admin-mng/team-create?teamName=" + teamName).then(() => { location.href = "/admin/admin-mng" });
}

function memberDetailUpdate(memberId) {
  (async () => {
    const result = await fetch("/admin/admin-mng/detail?memberId=" + memberId).then(res => res.text());
    $(".accounts-wrapper").replaceWith(result);
  })();
}

function adminDelete() {
  location.href = "/admin/admin-mng/delete?memberId=" + $("#detail-form #member-id").val();
}