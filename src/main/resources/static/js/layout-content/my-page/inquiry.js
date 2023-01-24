$(function () {
    $('.bottom_contents').click(function () {
        const inquiryBox = $(this).parent().find('#inquiry_box');
        if (inquiryBox.css('display') == 'none') {
            inquiryBox.css('display', 'block');
        } else {
            inquiryBox.css('display', 'none');
        }
    })
});

