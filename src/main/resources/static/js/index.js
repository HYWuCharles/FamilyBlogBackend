/*---------------------------------------------------------------------
    File Name: index.js
    This file controls network usage of index.html and elements update
---------------------------------------------------------------------*/
$(document).ready(function () {
    "use strict";

    // 检测是否已经登录，若未登录，跳转登录页
    let token = localStorage.getItem("jwtToken");
    if (token === null) $(location).attr('href', "/login");

    // 更新首页内容
    $.ajax({
        url: '/blog/mostRecent',
        type: 'get',
        dataType: 'json',
        headers: {
            "Authorization": token
        },
        success: function (data) {
            // console.log(data);
            // 更新首页数据
            let recentBlogs = data.data;
            for (let i = recentBlogs.length - 1; i > -1; i--) {
                let v = 4 - i;
                // 首先获取博客图片
                $.ajax({
                    url: '/blog/images/' + recentBlogs[i].id,
                    type: 'get',
                    dataType: 'json',
                    headers: {
                        "Authorization": token
                    },
                    success: function (d) {
                        // 使用第一张图片进行展示
                        // console.log(d)
                        if (d.data.length !== 0) {
                            // console.log("图片: " + d[0]);
                            $(".our_img:eq(" + v + ")").attr("src", d.data[0]);
                        }
                    }
                });
                // 更新标题
                $(".awesome:eq(" + v + ")").text(recentBlogs[i].title);
                // 设置标题点击事件
                $(".blog:eq(" + v + ")").attr("href", "/blog.html?blogId=" + recentBlogs[i].id);
                // console.log("第" + v + " 个标题: " + recentBlogs[i].title);
                // 更新内容
                $(".content:eq(" + v + ")").text(recentBlogs[i].content);
                // console.log("第" + v + " 个内容: " + recentBlogs[i].content);
                // 更新发布者
                $.ajax({
                    url: '/user/' + recentBlogs[i].createdBy,
                    type: 'get',
                    dataType: 'json',
                    headers: {
                        "Authorization": token
                    },
                    success: function (c) {
                        $(".flot_left1:eq(" + v + ")").text(c.data.nickName);
                        // console.log("第" + v + " 个坐着: " + c.data.nickName);
                    }
                });
            }
        }
    });

    // 更新最下方个人介绍
    $.ajax({
        url: "/user/current",
        type: 'get',
        dataType: 'json',
        headers: {
            "Authorization": token
        },
        success: function (data) {
            // console.log(data);
            $("#testimoial_image").attr("src", data.data.avatar);
            $(".about_me_nickname").text(data.data.nickName);
            $(".about_me_note").text(data.data.note);
        }
    });

    // 设置点击事件
    // 喜欢按钮
    $("")
});
