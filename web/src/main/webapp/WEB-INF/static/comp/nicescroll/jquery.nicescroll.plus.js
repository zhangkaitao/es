/* jquery.nicescroll.plus
 -- the addon for nicescroll
 -- version 1.0.0 BETA
 -- copyright 13 InuYaksa*2013
 -- licensed under the MIT
 --
 -- http://areaaperta.com/nicescroll
 -- https://github.com/inuyaksa/jquery.nicescroll
 --
 */
(function(jQuery){

    var $ = jQuery;  // sandbox

    if (!$||!("nicescroll" in $)) return;

    $.extend($.nicescroll.options,{

        styler:false

    });

    var _super = {
        "niceScroll":$.fn.niceScroll,
        "getNiceScroll":$.fn.getNiceScroll
    }

    $.fn.niceScroll = function(wrapper,opt) {

        if (!(typeof wrapper == "undefined")) {
            if (typeof wrapper == "object") {
                opt = wrapper;
                wrapper = false;
            }
        }

        var styler = (opt&&opt.styler)||$.nicescroll.options.styler||false;

        if (styler) {
            nw=preStyler(styler);
            $.extend(nw,opt);
            opt = nw;
        }

        var ret = _super.niceScroll.call(this,wrapper,opt);

        if (styler) doStyler(styler,ret);

        ret.scrollTo = function(el) {
            var off = this.win.position();
            var pos = this.win.find(el).position();
            if (pos) {
                var top = Math.floor(pos.top-off.top+this.scrollTop());
                this.doScrollTop(top);
            }
        }

        $(this).mouseover(function() {
            $(this).getNiceScroll().resize();
        });

        return ret;
    }

    $.fn.getNiceScroll = function(index) {
        var ret = _super.getNiceScroll.call(this,index);
        ret.scrollTo = function(el) {
            this.each(function(){
                this.scrollTo.call(this,el);
            });
        }
        return ret;
    }

    function preStyler(styler) {
        var opt = {};
        switch(styler) {
            case "fb":
                opt.autohidemode = false;
                opt.cursorcolor = "#7B7C7E";
                opt.railcolor = "";
                opt.cursoropacitymax = 0.8;
                opt.cursorwidth = 10;
                opt.cursorborder = "1px solid #868688";
                opt.cursorborderradius = "10px";
                break;
        }
        return opt;
    }

    function doStyler(styler,nc) {
        if (!nc.rail) return;

        switch(styler) {
            case "fb":

                nc.rail.css({
                    "-webkit-border-radius":"10px",
                    "-moz-border-radius":"10px",
                    "border-radius":"10px"
                });

                nc.cursor.css({width:3});

                var obj = (nc.ispage) ? nc.rail : nc.win;

            function endHover() {
                nc._stylerfbstate = false;
                nc.rail.css({
                    "backgroundColor":""
                });
                nc.cursor.stop().animate({width:3},200);
            }

                obj.hover(function(){
                        nc._stylerfbstate = true;
                        nc.rail.css({
                            "backgroundColor":"#CED0D3"
                        });
                        nc.cursor.stop().css({width:10});
                    },
                    function(){
                        if (nc.rail.drag) return;
                        endHover();
                    });

                $(document).mouseup(function(){
                    if (nc._stylerfbstate) endHover();
                });

                break;
        }

    }

})( jQuery );