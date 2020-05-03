/*---------------------------------------------------------------------
    File Name: blogs.js
    This file controls network usage of blogs.html and elements update
---------------------------------------------------------------------*/
$(document).ready(function () {
    "use strict";

    // 检测是否已经登录，若未登录，跳转登录页
    let token = localStorage.getItem("jwtToken");
    if (token === null) $(location).attr('href', "/login");

    let i = 0;

    let listPageAndUpdate = function (pageNum, pageSize) {
        console.log("Executed listandupdate");
        $.ajax({
            url: '/blog/list?pageNum=' + pageNum + "&pageSize=" + pageSize,
            type: 'get',
            dataType: 'json',
            headers: {
                "Authorization": token
            },
            success: function (data) {
                // console.log(data);
                // 更新首页数据
                let blogs = data.data;
                for (let i = 0; i < blogs.length; i++) {
                    // 首先获取博客图片
                    let title = blogs[i].title;
                    let content = blogs[i].content;
                    let src = "";
                    let link = "/blog.html?blogId=" + blogs[i].id;
                    let nickName = "";
                    $.ajax({
                        url: '/blog/images/' + blogs[i].id,
                        type: 'get',
                        dataType: 'json',
                        headers: {
                            "Authorization": token
                        },
                        success: function (d) {
                            // 使用第一张图片进行展示, 进行append
                            src = d.data[0];
                            // console.log(d)
                            $.ajax({
                                url: '/user/' + blogs[i].createdBy,
                                type: 'get',
                                dataType: 'json',
                                headers: {
                                    "Authorization": token
                                },
                                success: function (c) {
                                    nickName = c.data.nickName;
                                    $(".blog_content").append("<div class=\"row\">\n" +
                                        "            <div class=\"col-md-12\">\n" +
                                        "                <div class=\"our_two_box\">\n" +
                                        "                    <div class=\"row d_flex\">\n" +
                                        "                        <div class=\"col-xl-6 col-lg-6 col-md-12 col-sm-12\">\n" +
                                        "                            <div class=\"our_img\">\n" +
                                        "                                <figure><img id=\"first_section_image\" src=\"" + src +
                                        "\" alt=\"#\"/></figure>\n" +
                                        "                           </div>\n" +
                                        "                        </div>\n" +
                                        "                        <div class=\"col-xl-6 col-lg-6 col-md-12 col-sm-12\">\n" +
                                        "                           <div class=\"our_text_box\">\n" +
                                        "                              <a class=\"blog\" href=\"" + link +
                                        "\"><h3 class=\"awesome pa_wi\">" + title +
                                        "</h3></a>\n" +
                                        "                              <p class=\"content\">" + content +
                                        "</p>\n" +
                                        "                              <div class=\"post_box padding_bottom1\">\n" +
                                        "                                 <h4 class=\"flot_left1\">发布者：" + nickName +
                                        " </h4>\n" +
                                        "                              </div>\n" +
                                        "                           </div>\n" +
                                        "                        </div>\n" +
                                        "                     </div>\n" +
                                        "                  </div>\n" +
                                        "               </div>\n" +
                                        "            </div>");
                                }
                            });
                        }
                    });

                    // 更新发布者

                }
            }
        });
    }

    listPageAndUpdate(0, 5);

    $(".read_more").click(function () {
        i++;
        listPageAndUpdate(i, 5);
    });
});
