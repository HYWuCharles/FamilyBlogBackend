$(document).ready(function () {
    "use strict";

    function getUrlParam(name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        let r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r !== null) return unescape(r[2]);
        return null; //返回参数值
    }

    let token = localStorage.getItem("jwtToken");
    if (token === null) $(location).attr('href', "/login");
    let blogId = getUrlParam('blogId');

    // 获取博客内容
    $.ajax({
        url: '/blog/' + blogId,
        type: 'get',
        dataType: 'json',
        headers: {
            "Authorization": token
        },
        success: function (d) {
            // 更新标题
            $(".awesome").text(d.data.title);
            // 更新内容
            $(".content").text(d.data.content);

            // 获取图片信息
            $.ajax({
                url: '/blog/images/' + blogId,
                type: 'get',
                dataType: 'json',
                headers: {
                    "Authorization": token
                },
                success: function (d1) {
                    // console.log(d1.data );
                    // 拼接尾部信息
                    for (let i = 0; i < d1.data.length; i++) {
                        $("#images_show").append(
                            "<div class=\"col-lg-4 col-sm-6\">" +
                            "<img src=\"" + d1.data[i] + "\" class=\"img-responsive\"/>" +
                            "</div>");
                    }
                }
            });
        }
    });

});