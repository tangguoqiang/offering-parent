(function ($) {

    $.fn.extend({

        smartpaginator: function (options) {

            var settings = $.extend({

                totalrecords: 0,

                recordsperpage: 0,

                length: 10,

                next: '<img src="../common/media/images/lan_icon_right.png" />',

                prev: '<img src="../common/media/images/lan_icon_left.png" />',

                //first: '<img src="../resource/css/skin1/Simg/btn_pageLeft.png" />',

                //last: '<img src="../resource/css/skin1/Simg/btn_pageRight.png" />',

                go: 'Go',

                theme: 'green',

                display: 'double',

                initval: 1,

                datacontainer: '', //data container id

                dataelement: '', //children elements to be filtered e.g. tr or div

                onchange: null,

                controlsalways: false

            }, options);

            return this.each(function () {

                var currentPage = 0;

                var startPage = 0;

                var totalpages = parseInt(settings.totalrecords / settings.recordsperpage);

                if (settings.totalrecords % settings.recordsperpage > 0) totalpages++;

                var initialized = false;

                var container = $(this).addClass('pager').addClass(settings.theme);

                container.find('ul').remove();

                container.find('div').remove();

                container.find('span').remove();

                var dataContainer;

                var dataElements;

                if (settings.datacontainer != '') {

                    dataContainer = $('#' + settings.datacontainer);

                    dataElements = $('' + settings.dataelement + '', dataContainer);

                }

                var list = $('<ul/>');

                var btnPrev = $('<div/>').addClass("btnPrev").html(settings.prev).click(function () { if ($(this).hasClass('disabled')) return false; currentPage = parseInt(list.find('li a.active').text()) - 1; navigate(--currentPage); }).addClass('btn');

                var btnNext = $('<div/>').html(settings.next).click(function () { if ($(this).hasClass('disabled')) return false; currentPage = parseInt(list.find('li a.active').text()); navigate(currentPage); }).addClass('btn');

                var btnFirst = $('<div/>').addClass("btnFirst").html(settings.first).click(function () { if ($(this).hasClass('disabled')) return false; currentPage = 0; navigate(0); }).addClass('btn');

                var btnLast = $('<div/>').addClass("btnLast").html(settings.last).click(function () { if ($(this).hasClass('disabled')) return false; currentPage = totalpages - 1; navigate(currentPage); }).addClass('btn');

                var inputPage = $('<input/>').attr('type', 'text').addClass("lan_input").keydown(function (e) {

                    if (isTextSelected(inputPage)) inputPage.val('');

                    if (e.which >= 48 && e.which < 58) {

                        var value = parseInt(inputPage.val() + (e.which - 48));

                        if (!(value > 0 && value <= totalpages)) e.preventDefault();

                    }else if(e.which >= 96 && e.which < 106){ 
                    	var value = parseInt(inputPage.val() + (e.which - 96));

                        if (!(value > 0 && value <= totalpages)) e.preventDefault();
                    	
                    }else if (!(e.which == 8 || e.which == 46)) e.preventDefault();

                });

                var btnGo = $('<input/>').attr('type', 'button').attr('value', settings.go).addClass('btn btnGo').click(function () { if (inputPage.val() == '') return false; else { currentPage = parseInt(inputPage.val()) - 1; navigate(currentPage); } });

                var pageTotal = $('<div/>').addClass("pageTotal");
                
                container.append(btnFirst).append(btnPrev).append(list).append(btnNext).append(btnLast).append($('<div/>').append(inputPage));
                
                if (settings.display == 'single') {

                    btnGo.css('display', 'none');

                    inputPage.css('display', 'none');

                }

                buildNavigation(startPage);

                if (settings.initval == 0) settings.initval = 1;

                currentPage = settings.initval - 1;

                navigate(currentPage);

                initialized = true;

                function showLabels(pageIndex) {

                	pageTotal.find('span').remove();
                    
                    ///container.find('pageTotal').remove();
                    var upper = (pageIndex + 1) * settings.recordsperpage;

                    if (upper > settings.totalrecords) upper = settings.totalrecords;
                    if(settings.totalrecords == ''){
                    	pageTotal.append($('<span/>').text('共'))
                        .append($('<span/>').append($('<b/>').text("0")))
                        .append($('<span/>').text('条'))
                    }else{
                    	pageTotal.append($('<span/>').text('共'))
                        .append($('<span/>').append($('<b/>').text(settings.totalrecords)))
                        .append($('<span/>').text('条'))
                    }
                    /*pageTotal.append($('<span/>').text('共'))
                           .append($('<span/>').append($('<b/>').text(settings.totalrecords)))
                           .append($('<span/>').text('条'))*/
                           //console.log(settings.totalrecords);

                }

                function buildNavigation(startPage) {

                    list.find('li').remove();
                    
                    if(0 == totalpages)
                    {
                    	list.append($('<li/>')
                    			.append($('<a>').attr('id', (1)).addClass('pagerchange').addClass('normal')
                               .attr('href', 'javascript:void(0)')
                               .text(1)));
                    }
                    
//                    if (settings.totalrecords < settings.recordsperpage) return;
                    for (var i = startPage; i < startPage + settings.length; i++) {
                        
                    	if (i == totalpages) 
                    	{
                    		
                    		break;
                    	};

                        list.append($('<li/>')

                                   // .append($('<a>').attr('id', (i + 1)).addClass(settings.theme).addClass('normal')
                        		    .append($('<a>').attr('id', (i + 1)).addClass('pagerchange').addClass('normal')

                                    .attr('href', 'javascript:void(0)')

                                    .text(i + 1))

                                    .click(function () {

                                        currentPage = startPage + $(this).closest('li').prevAll().length;

                                        navigate(currentPage);

                                    }));

                    }

                    showLabels(startPage);

                    inputPage.val((startPage + 1));

                    //list.find('li a').addClass(settings.theme).removeClass('active');

                   // list.find('li:eq(0) a').addClass(settings.theme).addClass('active');
                    list.find('li a').addClass('pagerchange').removeClass('active');  

                    list.find('li:eq(0) a').addClass('pagerchange').addClass('active');

                    //set width of paginator

                    var sW = list.find('li:eq(0) a').outerWidth() + (parseInt(list.find('li:eq(0)').css('margin-left')) * 2);

                    var width = sW * list.find('li').length;

                    list.css({ width: width });

                    showRequiredButtons(startPage);

                }

                function navigate(topage) {

                    //make sure the page in between min and max page count

                    var index = topage;

                    var mid = settings.length / 2;

                    if (settings.length % 2 > 0) mid = (settings.length + 1) / 2;

                    var startIndex = 0;

                    if (topage >= 0 && topage < totalpages) {

                        if (topage >= mid) {

                            if (totalpages - topage > mid)

                                startIndex = topage - (mid - 1);

                            else if (totalpages > settings.length)

                                startIndex = totalpages - settings.length;

                        }

                        buildNavigation(startIndex); showLabels(currentPage);

                        list.find('li a').removeClass('active');

                        inputPage.val(currentPage + 1);

                        list.find('li a[id="' + (index + 1) + '"]').addClass('active');

                        var recordStartIndex = currentPage * settings.recordsperpage;

                        var recordsEndIndex = recordStartIndex + settings.recordsperpage;

                        if (recordsEndIndex > settings.totalrecords)

                            recordsEndIndex = settings.totalrecords % recordsEndIndex;

                        if (initialized) {

                            if (settings.onchange != null) {

                                settings.onchange((currentPage + 1), recordStartIndex, recordsEndIndex);

                            }

                        }

                        if (dataContainer != null) {

                            if (dataContainer.length > 0) {

                                //hide all elements first

                                dataElements.css('display', 'none');

                                //display elements that need to be displayed

                                if ($(dataElements[0]).find('th').length > 0) { //if there is a header, keep it visible always

                                    $(dataElements[0]).css('display', '');

                                    recordStartIndex++;

                                    recordsEndIndex++;

                                }

                                for (var i = recordStartIndex; i < recordsEndIndex; i++)

                                    $(dataElements[i]).css('display', '');

                            }

                        }



                        showRequiredButtons();

                    }

                }

                function showRequiredButtons() {

                    if (totalpages > settings.length) {

                        if (currentPage > 0) {

                            if (!settings.controlsalways) {   

                                btnPrev.css('display', '');  

                            }

                            else {

                                btnPrev.css('display', '').removeClass('disabled');

                            }

                        }

                        else {

                            if (!settings.controlsalways) {

                                btnPrev.css('display', 'none');

                            }

                            else {

                                btnPrev.css('display', '').addClass('disabled');

                            }

                        }
                    //btnPrev
                        if (currentPage > settings.length / 2 - 1) {

                            if (!settings.controlsalways) {

                                //btnFirst.css('display', '');
                                //btnFirst.css({'opacity': '0',"margin-left":"32%"});
                            	btnFirst.css('opacity', '1');
                                
                            }

                            else {

                                //btnFirst.css('display', '').removeClass('disabled');
                                //btnFirst.css({'opacity': '0',"margin-left":"32%"});
                                btnFirst.css('opacity', '0').removeClass('disabled');

                            }

                        }

                        else {

                            if (!settings.controlsalways) {
                            	//btnFirst.css("margin-left","0");
                                btnFirst.css({'opacity': '0',"margin-left":"32%"});
                                 
                            }

                            else {

                                btnFirst.css('opacity', '0').addClass('disabled');
                            	// btnFirst.css('opacity', '0').addClass('disabled');
                            }

                        }



                        if (currentPage == totalpages - 1) {

                            if (!settings.controlsalways) {

                                btnNext.css('display', 'none');

                            }

                            else {

                                btnNext.css('display', '').addClass('disabled');

                            }

                        }

                        else {

                            if (!settings.controlsalways) {

                                btnNext.css('display', '');

                            }

                            else {

                                btnNext.css('display', '').removeClass('disabled');

                            }

                        }

                        if (totalpages > settings.length && currentPage < (totalpages - (settings.length / 2)) - 1) {

                            if (!settings.controlsalways) {

                                btnLast.css('display', '');
                                
                            }

                            else {

                                btnLast.css('display', '').removeClass('disabled');

                            }

                        }

                        else {

                            if (!settings.controlsalways) {

                                btnLast.css('display', 'none');
                                btnFirst.css({'opacity': '1',"margin-left":"32%"}); 
                                
                            }

                            else {

                                btnLast.css('display', '').addClass('disabled');

                            }

                        };

                    }

                    else {

                        if (!settings.controlsalways) {

                            btnFirst.css({'opacity': '0','disabled': 'disabled'});

                            btnPrev.css('display', 'block');

                            btnNext.css('display', 'block');

                            btnLast.css('display', 'none');

                        }

                        else {

                            btnFirst.css('display', '').addClass('disabled');

                            btnPrev.css('display', '').addClass('disabled');

                            btnNext.css('display', '').addClass('disabled');

                            btnLast.css('display', '').addClass('disabled');

                        }

                    }

                }

                function isTextSelected(el) {

                    var startPos = el.get(0).selectionStart;

                    var endPos = el.get(0).selectionEnd;

                    var doc = document.selection;

                    if (doc && doc.createRange().text.length != 0) {

                        return true;

                    } else if (!doc && el.val().substring(startPos, endPos).length != 0) {

                        return true;

                    }

                    return false;

                }

            });

        }

    });

})(jQuery);