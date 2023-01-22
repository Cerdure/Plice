
$(function(){
$('.bottom_contents').click(function () {
    const inquiryBox = $(this).parent().find('#inquiry_box');
    console.log("click")
    if (inquiryBox.css('display') == 'none') {
        inquiryBox.css('display', 'block');
    } else {
        inquiryBox.css('display', 'none');
    }
})
});



// $(function(){
//     $('#bottom_contents').click(function () {
//         if ($('#inquiry_box').css('display') == 'block') {
//             $('#inquiry_box').css('display', 'block');
           
//         } else {
//             $('#inquiry_box').css('display', 'none');
//         }
//     })
//     });
    
    
    