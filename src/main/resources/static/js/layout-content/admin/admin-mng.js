$(function () {

  memberDetailUpdate($("#admin-1").data("id"));

  $(document).on("click", ".team-group .member", e => {
    memberDetailUpdate(e.target.dataset.id);
  });

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

  $(document).on("click", ".create-admin-btn", o => {
    $(".modal-background").fadeIn(100);
    $(".admin-create-window").fadeIn(300);
  });

  $(document).on("click", ".delete-btn", o => {
    $(".modal-background").fadeIn(100);
    $(".del-alert").fadeIn(300);
  });

  $(document).on("click", ".create-team-btn", o => {
    $(".modal-background").fadeIn(100);
    $(".team-create-window").fadeIn(300);
  });

  $(document).on("click", ".team-delete-btn", function () {
    const labelBox = $(this).closest(".label-box");
    const name = labelBox.data("name");
    const memberCount = labelBox.data("member-count");
    if (memberCount != '0') {
      alert("소속원이 있는 부서는 삭제할 수 없습니다.")
    } else {
      teamId = labelBox.data("id");
      $(".del-team-alert .data").text(name);
      $(".modal-background").fadeIn(100);
      $(".del-team-alert").fadeIn(300);
    }
  });

});

let teamId;

function hideAlert() {
  $(".alert-window").hide();
  $(".modal-background").hide();
  $(".alert-window input").val('');
}

function teamCreate() {
  const teamName = $(".team-create-window input").val();
  fetch("/admin/admin-mng/team-create?teamName=" + teamName).then(() => { location.href = "/admin/admin-mng" });
}

function teamDelete() {
  fetch("/admin/admin-mng/delete/team?teamId=" + teamId).then(res => {
    if (res.json()) {
      alert("삭제되었습니다.");
      location.reload();
    } else {
      alert("요청이 실패하였습니다.");
    }
  });
}

function memberDetailUpdate(memberId) {
  (async () => {
    const result = await fetch("/admin/admin-mng/detail?memberId=" + memberId).then(res => res.text());
    $(".accounts-wrapper").replaceWith(result);
  })();
}

function adminDelete() {
  fetch("/admin/admin-mng/delete?memberId=" + $("#detail-form #member-id").val()).then(res => {
    if (res.json()) {
      alert("삭제되었습니다.");
      location.reload();
    } else {
      alert("요청이 실패하였습니다.");
    }
  });
}