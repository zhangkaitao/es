/* jQuery UI - v1.10.3 - 2013-10-07
* http://jqueryui.com
* Includes: jquery.ui.core.js, jquery.ui.widget.js, jquery.ui.mouse.js, jquery.ui.position.js, jquery.ui.draggable.js, jquery.ui.droppable.js, jquery.ui.resizable.js, jquery.ui.selectable.js, jquery.ui.sortable.js, jquery.ui.accordion.js, jquery.ui.autocomplete.js, jquery.ui.button.js, jquery.ui.dialog.js, jquery.ui.menu.js, jquery.ui.progressbar.js, jquery.ui.slider.js, jquery.ui.spinner.js, jquery.ui.tabs.js, jquery.ui.tooltip.js, jquery.ui.effect.js, jquery.ui.effect-blind.js, jquery.ui.effect-bounce.js, jquery.ui.effect-clip.js, jquery.ui.effect-drop.js, jquery.ui.effect-explode.js, jquery.ui.effect-fade.js, jquery.ui.effect-fold.js, jquery.ui.effect-highlight.js, jquery.ui.effect-pulsate.js, jquery.ui.effect-scale.js, jquery.ui.effect-shake.js, jquery.ui.effect-slide.js, jquery.ui.effect-transfer.js
* Copyright 2013 jQuery Foundation and other contributors; Licensed MIT */
(function(b,f){var a=0,e=/^ui-id-\d+$/;
b.ui=b.ui||{};
b.extend(b.ui,{version:"1.10.3",keyCode:{BACKSPACE:8,COMMA:188,DELETE:46,DOWN:40,END:35,ENTER:13,ESCAPE:27,HOME:36,LEFT:37,NUMPAD_ADD:107,NUMPAD_DECIMAL:110,NUMPAD_DIVIDE:111,NUMPAD_ENTER:108,NUMPAD_MULTIPLY:106,NUMPAD_SUBTRACT:109,PAGE_DOWN:34,PAGE_UP:33,PERIOD:190,RIGHT:39,SPACE:32,TAB:9,UP:38}});
b.fn.extend({focus:(function(g){return function(h,i){return typeof h==="number"?this.each(function(){var j=this;
setTimeout(function(){b(j).focus();
if(i){i.call(j)
}},h)}):g.apply(this,arguments)
}})(b.fn.focus),scrollParent:function(){var g;
if((b.ui.ie&&(/(static|relative)/).test(this.css("position")))||(/absolute/).test(this.css("position"))){g=this.parents().filter(function(){return(/(relative|absolute|fixed)/).test(b.css(this,"position"))&&(/(auto|scroll)/).test(b.css(this,"overflow")+b.css(this,"overflow-y")+b.css(this,"overflow-x"))
}).eq(0)}else{g=this.parents().filter(function(){return(/(auto|scroll)/).test(b.css(this,"overflow")+b.css(this,"overflow-y")+b.css(this,"overflow-x"))
}).eq(0)}return(/fixed/).test(this.css("position"))||!g.length?b(document):g
},zIndex:function(j){if(j!==f){return this.css("zIndex",j)
}if(this.length){var h=b(this[0]),g,i;
while(h.length&&h[0]!==document){g=h.css("position");
if(g==="absolute"||g==="relative"||g==="fixed"){i=parseInt(h.css("zIndex"),10);
if(!isNaN(i)&&i!==0){return i
}}h=h.parent()
}}return 0},uniqueId:function(){return this.each(function(){if(!this.id){this.id="ui-id-"+(++a)
}})},removeUniqueId:function(){return this.each(function(){if(e.test(this.id)){b(this).removeAttr("id")
}})}});function d(i,g){var k,j,h,l=i.nodeName.toLowerCase();
if("area"===l){k=i.parentNode;
j=k.name;if(!i.href||!j||k.nodeName.toLowerCase()!=="map"){return false
}h=b("img[usemap=#"+j+"]")[0];
return !!h&&c(h)
}return(/input|select|textarea|button|object/.test(l)?!i.disabled:"a"===l?i.href||g:g)&&c(i)
}function c(g){return b.expr.filters.visible(g)&&!b(g).parents().addBack().filter(function(){return b.css(this,"visibility")==="hidden"
}).length}b.extend(b.expr[":"],{data:b.expr.createPseudo?b.expr.createPseudo(function(g){return function(h){return !!b.data(h,g)
}}):function(j,h,g){return !!b.data(j,g[3])
},focusable:function(g){return d(g,!isNaN(b.attr(g,"tabindex")))
},tabbable:function(i){var g=b.attr(i,"tabindex"),h=isNaN(g);
return(h||g>=0)&&d(i,!h)
}});if(!b("<a>").outerWidth(1).jquery){b.each(["Width","Height"],function(j,g){var h=g==="Width"?["Left","Right"]:["Top","Bottom"],k=g.toLowerCase(),m={innerWidth:b.fn.innerWidth,innerHeight:b.fn.innerHeight,outerWidth:b.fn.outerWidth,outerHeight:b.fn.outerHeight};
function l(o,n,i,p){b.each(h,function(){n-=parseFloat(b.css(o,"padding"+this))||0;
if(i){n-=parseFloat(b.css(o,"border"+this+"Width"))||0
}if(p){n-=parseFloat(b.css(o,"margin"+this))||0
}});return n
}b.fn["inner"+g]=function(i){if(i===f){return m["inner"+g].call(this)
}return this.each(function(){b(this).css(k,l(this,i)+"px")
})};b.fn["outer"+g]=function(i,n){if(typeof i!=="number"){return m["outer"+g].call(this,i)
}return this.each(function(){b(this).css(k,l(this,i,true,n)+"px")
})}})}if(!b.fn.addBack){b.fn.addBack=function(g){return this.add(g==null?this.prevObject:this.prevObject.filter(g))
}}if(b("<a>").data("a-b","a").removeData("a-b").data("a-b")){b.fn.removeData=(function(g){return function(h){if(arguments.length){return g.call(this,b.camelCase(h))
}else{return g.call(this)
}}})(b.fn.removeData)
}b.ui.ie=!!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase());
b.support.selectstart="onselectstart" in document.createElement("div");
b.fn.extend({disableSelection:function(){return this.bind((b.support.selectstart?"selectstart":"mousedown")+".ui-disableSelection",function(g){g.preventDefault()
})},enableSelection:function(){return this.unbind(".ui-disableSelection")
}});b.extend(b.ui,{plugin:{add:function(h,j,l){var g,k=b.ui[h].prototype;
for(g in l){k.plugins[g]=k.plugins[g]||[];
k.plugins[g].push([j,l[g]])
}},call:function(g,j,h){var k,l=g.plugins[j];
if(!l||!g.element[0].parentNode||g.element[0].parentNode.nodeType===11){return
}for(k=0;k<l.length;
k++){if(g.options[l[k][0]]){l[k][1].apply(g.element,h)
}}}},hasScroll:function(j,h){if(b(j).css("overflow")==="hidden"){return false
}var g=(h&&h==="left")?"scrollLeft":"scrollTop",i=false;
if(j[g]>0){return true
}j[g]=1;i=(j[g]>0);
j[g]=0;return i
}})})(jQuery);
(function(b,e){var a=0,d=Array.prototype.slice,c=b.cleanData;
b.cleanData=function(f){for(var g=0,h;
(h=f[g])!=null;
g++){try{b(h).triggerHandler("remove")
}catch(j){}}c(f)
};b.widget=function(f,g,n){var k,l,i,m,h={},j=f.split(".")[0];
f=f.split(".")[1];
k=j+"-"+f;if(!n){n=g;
g=b.Widget}b.expr[":"][k.toLowerCase()]=function(o){return !!b.data(o,k)
};b[j]=b[j]||{};
l=b[j][f];i=b[j][f]=function(o,p){if(!this._createWidget){return new i(o,p)
}if(arguments.length){this._createWidget(o,p)
}};b.extend(i,l,{version:n.version,_proto:b.extend({},n),_childConstructors:[]});
m=new g();m.options=b.widget.extend({},m.options);
b.each(n,function(p,o){if(!b.isFunction(o)){h[p]=o;
return}h[p]=(function(){var q=function(){return g.prototype[p].apply(this,arguments)
},r=function(s){return g.prototype[p].apply(this,s)
};return function(){var u=this._super,s=this._superApply,t;
this._super=q;
this._superApply=r;
t=o.apply(this,arguments);
this._super=u;
this._superApply=s;
return t}})()
});i.prototype=b.widget.extend(m,{widgetEventPrefix:l?m.widgetEventPrefix:f},h,{constructor:i,namespace:j,widgetName:f,widgetFullName:k});
if(l){b.each(l._childConstructors,function(p,q){var o=q.prototype;
b.widget(o.namespace+"."+o.widgetName,i,q._proto)
});delete l._childConstructors
}else{g._childConstructors.push(i)
}b.widget.bridge(f,i)
};b.widget.extend=function(k){var g=d.call(arguments,1),j=0,f=g.length,h,i;
for(;j<f;j++){for(h in g[j]){i=g[j][h];
if(g[j].hasOwnProperty(h)&&i!==e){if(b.isPlainObject(i)){k[h]=b.isPlainObject(k[h])?b.widget.extend({},k[h],i):b.widget.extend({},i)
}else{k[h]=i
}}}}return k
};b.widget.bridge=function(g,f){var h=f.prototype.widgetFullName||g;
b.fn[g]=function(k){var i=typeof k==="string",j=d.call(arguments,1),l=this;
k=!i&&j.length?b.widget.extend.apply(null,[k].concat(j)):k;
if(i){this.each(function(){var n,m=b.data(this,h);
if(!m){return b.error("cannot call methods on "+g+" prior to initialization; attempted to call method '"+k+"'")
}if(!b.isFunction(m[k])||k.charAt(0)==="_"){return b.error("no such method '"+k+"' for "+g+" widget instance")
}n=m[k].apply(m,j);
if(n!==m&&n!==e){l=n&&n.jquery?l.pushStack(n.get()):n;
return false
}})}else{this.each(function(){var m=b.data(this,h);
if(m){m.option(k||{})._init()
}else{b.data(this,h,new f(k,this))
}})}return l
}};b.Widget=function(){};
b.Widget._childConstructors=[];
b.Widget.prototype={widgetName:"widget",widgetEventPrefix:"",defaultElement:"<div>",options:{disabled:false,create:null},_createWidget:function(f,g){g=b(g||this.defaultElement||this)[0];
this.element=b(g);
this.uuid=a++;
this.eventNamespace="."+this.widgetName+this.uuid;
this.options=b.widget.extend({},this.options,this._getCreateOptions(),f);
this.bindings=b();
this.hoverable=b();
this.focusable=b();
if(g!==this){b.data(g,this.widgetFullName,this);
this._on(true,this.element,{remove:function(h){if(h.target===g){this.destroy()
}}});this.document=b(g.style?g.ownerDocument:g.document||g);
this.window=b(this.document[0].defaultView||this.document[0].parentWindow)
}this._create();
this._trigger("create",null,this._getCreateEventData());
this._init()
},_getCreateOptions:b.noop,_getCreateEventData:b.noop,_create:b.noop,_init:b.noop,destroy:function(){this._destroy();
this.element.unbind(this.eventNamespace).removeData(this.widgetName).removeData(this.widgetFullName).removeData(b.camelCase(this.widgetFullName));
this.widget().unbind(this.eventNamespace).removeAttr("aria-disabled").removeClass(this.widgetFullName+"-disabled ui-state-disabled");
this.bindings.unbind(this.eventNamespace);
this.hoverable.removeClass("ui-state-hover");
this.focusable.removeClass("ui-state-focus")
},_destroy:b.noop,widget:function(){return this.element
},option:function(j,k){var f=j,l,h,g;
if(arguments.length===0){return b.widget.extend({},this.options)
}if(typeof j==="string"){f={};
l=j.split(".");
j=l.shift();
if(l.length){h=f[j]=b.widget.extend({},this.options[j]);
for(g=0;g<l.length-1;
g++){h[l[g]]=h[l[g]]||{};
h=h[l[g]]}j=l.pop();
if(k===e){return h[j]===e?null:h[j]
}h[j]=k}else{if(k===e){return this.options[j]===e?null:this.options[j]
}f[j]=k}}this._setOptions(f);
return this
},_setOptions:function(f){var g;
for(g in f){this._setOption(g,f[g])
}return this
},_setOption:function(f,g){this.options[f]=g;
if(f==="disabled"){this.widget().toggleClass(this.widgetFullName+"-disabled ui-state-disabled",!!g).attr("aria-disabled",g);
this.hoverable.removeClass("ui-state-hover");
this.focusable.removeClass("ui-state-focus")
}return this
},enable:function(){return this._setOption("disabled",false)
},disable:function(){return this._setOption("disabled",true)
},_on:function(i,h,g){var j,f=this;
if(typeof i!=="boolean"){g=h;
h=i;i=false
}if(!g){g=h;
h=this.element;
j=this.widget()
}else{h=j=b(h);
this.bindings=this.bindings.add(h)
}b.each(g,function(p,o){function m(){if(!i&&(f.options.disabled===true||b(this).hasClass("ui-state-disabled"))){return
}return(typeof o==="string"?f[o]:o).apply(f,arguments)
}if(typeof o!=="string"){m.guid=o.guid=o.guid||m.guid||b.guid++
}var n=p.match(/^(\w+)\s*(.*)$/),l=n[1]+f.eventNamespace,k=n[2];
if(k){j.delegate(k,l,m)
}else{h.bind(l,m)
}})},_off:function(g,f){f=(f||"").split(" ").join(this.eventNamespace+" ")+this.eventNamespace;
g.unbind(f).undelegate(f)
},_delay:function(i,h){function g(){return(typeof i==="string"?f[i]:i).apply(f,arguments)
}var f=this;
return setTimeout(g,h||0)
},_hoverable:function(f){this.hoverable=this.hoverable.add(f);
this._on(f,{mouseenter:function(g){b(g.currentTarget).addClass("ui-state-hover")
},mouseleave:function(g){b(g.currentTarget).removeClass("ui-state-hover")
}})},_focusable:function(f){this.focusable=this.focusable.add(f);
this._on(f,{focusin:function(g){b(g.currentTarget).addClass("ui-state-focus")
},focusout:function(g){b(g.currentTarget).removeClass("ui-state-focus")
}})},_trigger:function(f,g,h){var k,j,i=this.options[f];
h=h||{};g=b.Event(g);
g.type=(f===this.widgetEventPrefix?f:this.widgetEventPrefix+f).toLowerCase();
g.target=this.element[0];
j=g.originalEvent;
if(j){for(k in j){if(!(k in g)){g[k]=j[k]
}}}this.element.trigger(g,h);
return !(b.isFunction(i)&&i.apply(this.element[0],[g].concat(h))===false||g.isDefaultPrevented())
}};b.each({show:"fadeIn",hide:"fadeOut"},function(g,f){b.Widget.prototype["_"+g]=function(j,i,l){if(typeof i==="string"){i={effect:i}
}var k,h=!i?g:i===true||typeof i==="number"?f:i.effect||f;
i=i||{};if(typeof i==="number"){i={duration:i}
}k=!b.isEmptyObject(i);
i.complete=l;
if(i.delay){j.delay(i.delay)
}if(k&&b.effects&&b.effects.effect[h]){j[g](i)
}else{if(h!==g&&j[h]){j[h](i.duration,i.easing,l)
}else{j.queue(function(m){b(this)[g]();
if(l){l.call(j[0])
}m()})}}}})
})(jQuery);
(function(b,c){var a=false;
b(document).mouseup(function(){a=false
});b.widget("ui.mouse",{version:"1.10.3",options:{cancel:"input,textarea,button,select,option",distance:1,delay:0},_mouseInit:function(){var d=this;
this.element.bind("mousedown."+this.widgetName,function(e){return d._mouseDown(e)
}).bind("click."+this.widgetName,function(e){if(true===b.data(e.target,d.widgetName+".preventClickEvent")){b.removeData(e.target,d.widgetName+".preventClickEvent");
e.stopImmediatePropagation();
return false
}});this.started=false
},_mouseDestroy:function(){this.element.unbind("."+this.widgetName);
if(this._mouseMoveDelegate){b(document).unbind("mousemove."+this.widgetName,this._mouseMoveDelegate).unbind("mouseup."+this.widgetName,this._mouseUpDelegate)
}},_mouseDown:function(f){if(a){return
}(this._mouseStarted&&this._mouseUp(f));
this._mouseDownEvent=f;
var e=this,g=(f.which===1),d=(typeof this.options.cancel==="string"&&f.target.nodeName?b(f.target).closest(this.options.cancel).length:false);
if(!g||d||!this._mouseCapture(f)){return true
}this.mouseDelayMet=!this.options.delay;
if(!this.mouseDelayMet){this._mouseDelayTimer=setTimeout(function(){e.mouseDelayMet=true
},this.options.delay)
}if(this._mouseDistanceMet(f)&&this._mouseDelayMet(f)){this._mouseStarted=(this._mouseStart(f)!==false);
if(!this._mouseStarted){f.preventDefault();
return true
}}if(true===b.data(f.target,this.widgetName+".preventClickEvent")){b.removeData(f.target,this.widgetName+".preventClickEvent")
}this._mouseMoveDelegate=function(h){return e._mouseMove(h)
};this._mouseUpDelegate=function(h){return e._mouseUp(h)
};b(document).bind("mousemove."+this.widgetName,this._mouseMoveDelegate).bind("mouseup."+this.widgetName,this._mouseUpDelegate);
f.preventDefault();
a=true;return true
},_mouseMove:function(d){if(b.ui.ie&&(!document.documentMode||document.documentMode<9)&&!d.button){return this._mouseUp(d)
}if(this._mouseStarted){this._mouseDrag(d);
return d.preventDefault()
}if(this._mouseDistanceMet(d)&&this._mouseDelayMet(d)){this._mouseStarted=(this._mouseStart(this._mouseDownEvent,d)!==false);
(this._mouseStarted?this._mouseDrag(d):this._mouseUp(d))
}return !this._mouseStarted
},_mouseUp:function(d){b(document).unbind("mousemove."+this.widgetName,this._mouseMoveDelegate).unbind("mouseup."+this.widgetName,this._mouseUpDelegate);
if(this._mouseStarted){this._mouseStarted=false;
if(d.target===this._mouseDownEvent.target){b.data(d.target,this.widgetName+".preventClickEvent",true)
}this._mouseStop(d)
}return false
},_mouseDistanceMet:function(d){return(Math.max(Math.abs(this._mouseDownEvent.pageX-d.pageX),Math.abs(this._mouseDownEvent.pageY-d.pageY))>=this.options.distance)
},_mouseDelayMet:function(){return this.mouseDelayMet
},_mouseStart:function(){},_mouseDrag:function(){},_mouseStop:function(){},_mouseCapture:function(){return true
}})})(jQuery);
(function(e,c){e.ui=e.ui||{};
var j,k=Math.max,o=Math.abs,m=Math.round,d=/left|center|right/,h=/top|center|bottom/,a=/[\+\-]\d+(\.[\d]+)?%?/,l=/^\w+/,b=/%$/,g=e.fn.position;
function n(r,q,p){return[parseFloat(r[0])*(b.test(r[0])?q/100:1),parseFloat(r[1])*(b.test(r[1])?p/100:1)]
}function i(p,q){return parseInt(e.css(p,q),10)||0
}function f(q){var p=q[0];
if(p.nodeType===9){return{width:q.width(),height:q.height(),offset:{top:0,left:0}}
}if(e.isWindow(p)){return{width:q.width(),height:q.height(),offset:{top:q.scrollTop(),left:q.scrollLeft()}}
}if(p.preventDefault){return{width:0,height:0,offset:{top:p.pageY,left:p.pageX}}
}return{width:q.outerWidth(),height:q.outerHeight(),offset:q.offset()}
}e.position={scrollbarWidth:function(){if(j!==c){return j
}var q,p,s=e("<div style='display:block;width:50px;height:50px;overflow:hidden;'><div style='height:100px;width:auto;'></div></div>"),r=s.children()[0];
e("body").append(s);
q=r.offsetWidth;
s.css("overflow","scroll");
p=r.offsetWidth;
if(q===p){p=s[0].clientWidth
}s.remove();
return(j=q-p)
},getScrollInfo:function(t){var s=t.isWindow?"":t.element.css("overflow-x"),r=t.isWindow?"":t.element.css("overflow-y"),q=s==="scroll"||(s==="auto"&&t.width<t.element[0].scrollWidth),p=r==="scroll"||(r==="auto"&&t.height<t.element[0].scrollHeight);
return{width:p?e.position.scrollbarWidth():0,height:q?e.position.scrollbarWidth():0}
},getWithinInfo:function(q){var r=e(q||window),p=e.isWindow(r[0]);
return{element:r,isWindow:p,offset:r.offset()||{left:0,top:0},scrollLeft:r.scrollLeft(),scrollTop:r.scrollTop(),width:p?r.width():r.outerWidth(),height:p?r.height():r.outerHeight()}
}};e.fn.position=function(z){if(!z||!z.of){return g.apply(this,arguments)
}z=e.extend({},z);
var A,w,u,y,t,p,v=e(z.of),s=e.position.getWithinInfo(z.within),q=e.position.getScrollInfo(s),x=(z.collision||"flip").split(" "),r={};
p=f(v);if(v[0].preventDefault){z.at="left top"
}w=p.width;
u=p.height;
y=p.offset;
t=e.extend({},y);
e.each(["my","at"],function(){var D=(z[this]||"").split(" "),C,B;
if(D.length===1){D=d.test(D[0])?D.concat(["center"]):h.test(D[0])?["center"].concat(D):["center","center"]
}D[0]=d.test(D[0])?D[0]:"center";
D[1]=h.test(D[1])?D[1]:"center";
C=a.exec(D[0]);
B=a.exec(D[1]);
r[this]=[C?C[0]:0,B?B[0]:0];
z[this]=[l.exec(D[0])[0],l.exec(D[1])[0]]
});if(x.length===1){x[1]=x[0]
}if(z.at[0]==="right"){t.left+=w
}else{if(z.at[0]==="center"){t.left+=w/2
}}if(z.at[1]==="bottom"){t.top+=u
}else{if(z.at[1]==="center"){t.top+=u/2
}}A=n(r.at,w,u);
t.left+=A[0];
t.top+=A[1];
return this.each(function(){var C,L,E=e(this),G=E.outerWidth(),D=E.outerHeight(),F=i(this,"marginLeft"),B=i(this,"marginTop"),K=G+F+i(this,"marginRight")+q.width,J=D+B+i(this,"marginBottom")+q.height,H=e.extend({},t),I=n(r.my,E.outerWidth(),E.outerHeight());
if(z.my[0]==="right"){H.left-=G
}else{if(z.my[0]==="center"){H.left-=G/2
}}if(z.my[1]==="bottom"){H.top-=D
}else{if(z.my[1]==="center"){H.top-=D/2
}}H.left+=I[0];
H.top+=I[1];
if(!e.support.offsetFractions){H.left=m(H.left);
H.top=m(H.top)
}C={marginLeft:F,marginTop:B};
e.each(["left","top"],function(N,M){if(e.ui.position[x[N]]){e.ui.position[x[N]][M](H,{targetWidth:w,targetHeight:u,elemWidth:G,elemHeight:D,collisionPosition:C,collisionWidth:K,collisionHeight:J,offset:[A[0]+I[0],A[1]+I[1]],my:z.my,at:z.at,within:s,elem:E})
}});if(z.using){L=function(P){var R=y.left-H.left,O=R+w-G,Q=y.top-H.top,N=Q+u-D,M={target:{element:v,left:y.left,top:y.top,width:w,height:u},element:{element:E,left:H.left,top:H.top,width:G,height:D},horizontal:O<0?"left":R>0?"right":"center",vertical:N<0?"top":Q>0?"bottom":"middle"};
if(w<G&&o(R+O)<w){M.horizontal="center"
}if(u<D&&o(Q+N)<u){M.vertical="middle"
}if(k(o(R),o(O))>k(o(Q),o(N))){M.important="horizontal"
}else{M.important="vertical"
}z.using.call(this,P,M)
}}E.offset(e.extend(H,{using:L}))
})};e.ui.position={fit:{left:function(t,s){var r=s.within,v=r.isWindow?r.scrollLeft:r.offset.left,x=r.width,u=t.left-s.collisionPosition.marginLeft,w=v-u,q=u+s.collisionWidth-x-v,p;
if(s.collisionWidth>x){if(w>0&&q<=0){p=t.left+w+s.collisionWidth-x-v;
t.left+=w-p
}else{if(q>0&&w<=0){t.left=v
}else{if(w>q){t.left=v+x-s.collisionWidth
}else{t.left=v
}}}}else{if(w>0){t.left+=w
}else{if(q>0){t.left-=q
}else{t.left=k(t.left-u,t.left)
}}}},top:function(s,r){var q=r.within,w=q.isWindow?q.scrollTop:q.offset.top,x=r.within.height,u=s.top-r.collisionPosition.marginTop,v=w-u,t=u+r.collisionHeight-x-w,p;
if(r.collisionHeight>x){if(v>0&&t<=0){p=s.top+v+r.collisionHeight-x-w;
s.top+=v-p}else{if(t>0&&v<=0){s.top=w
}else{if(v>t){s.top=w+x-r.collisionHeight
}else{s.top=w
}}}}else{if(v>0){s.top+=v
}else{if(t>0){s.top-=t
}else{s.top=k(s.top-u,s.top)
}}}}},flip:{left:function(v,u){var t=u.within,z=t.offset.left+t.scrollLeft,C=t.width,r=t.isWindow?t.scrollLeft:t.offset.left,w=v.left-u.collisionPosition.marginLeft,A=w-r,q=w+u.collisionWidth-C-r,y=u.my[0]==="left"?-u.elemWidth:u.my[0]==="right"?u.elemWidth:0,B=u.at[0]==="left"?u.targetWidth:u.at[0]==="right"?-u.targetWidth:0,s=-2*u.offset[0],p,x;
if(A<0){p=v.left+y+B+s+u.collisionWidth-C-z;
if(p<0||p<o(A)){v.left+=y+B+s
}}else{if(q>0){x=v.left-u.collisionPosition.marginLeft+y+B+s-r;
if(x>0||o(x)<q){v.left+=y+B+s
}}}},top:function(u,t){var s=t.within,B=s.offset.top+s.scrollTop,C=s.height,p=s.isWindow?s.scrollTop:s.offset.top,w=u.top-t.collisionPosition.marginTop,y=w-p,v=w+t.collisionHeight-C-p,z=t.my[1]==="top",x=z?-t.elemHeight:t.my[1]==="bottom"?t.elemHeight:0,D=t.at[1]==="top"?t.targetHeight:t.at[1]==="bottom"?-t.targetHeight:0,r=-2*t.offset[1],A,q;
if(y<0){q=u.top+x+D+r+t.collisionHeight-C-B;
if((u.top+x+D+r)>y&&(q<0||q<o(y))){u.top+=x+D+r
}}else{if(v>0){A=u.top-t.collisionPosition.marginTop+x+D+r-p;
if((u.top+x+D+r)>v&&(A>0||o(A)<v)){u.top+=x+D+r
}}}}},flipfit:{left:function(){e.ui.position.flip.left.apply(this,arguments);
e.ui.position.fit.left.apply(this,arguments)
},top:function(){e.ui.position.flip.top.apply(this,arguments);
e.ui.position.fit.top.apply(this,arguments)
}}};(function(){var t,v,q,s,r,p=document.getElementsByTagName("body")[0],u=document.createElement("div");
t=document.createElement(p?"div":"body");
q={visibility:"hidden",width:0,height:0,border:0,margin:0,background:"none"};
if(p){e.extend(q,{position:"absolute",left:"-1000px",top:"-1000px"})
}for(r in q){t.style[r]=q[r]
}t.appendChild(u);
v=p||document.documentElement;
v.insertBefore(t,v.firstChild);
u.style.cssText="position: absolute; left: 10.7432222px;";
s=e(u).offset().left;
e.support.offsetFractions=s>10&&s<11;
t.innerHTML="";
v.removeChild(t)
})()}(jQuery));
(function(a,b){a.widget("ui.draggable",a.ui.mouse,{version:"1.10.3",widgetEventPrefix:"drag",options:{addClasses:true,appendTo:"parent",axis:false,connectToSortable:false,containment:false,cursor:"auto",cursorAt:false,grid:false,handle:false,helper:"original",iframeFix:false,opacity:false,refreshPositions:false,revert:false,revertDuration:500,scope:"default",scroll:true,scrollSensitivity:20,scrollSpeed:20,snap:false,snapMode:"both",snapTolerance:20,stack:false,zIndex:false,drag:null,start:null,stop:null},_create:function(){if(this.options.helper==="original"&&!(/^(?:r|a|f)/).test(this.element.css("position"))){this.element[0].style.position="relative"
}if(this.options.addClasses){this.element.addClass("ui-draggable")
}if(this.options.disabled){this.element.addClass("ui-draggable-disabled")
}this._mouseInit()
},_destroy:function(){this.element.removeClass("ui-draggable ui-draggable-dragging ui-draggable-disabled");
this._mouseDestroy()
},_mouseCapture:function(c){var d=this.options;
if(this.helper||d.disabled||a(c.target).closest(".ui-resizable-handle").length>0){return false
}this.handle=this._getHandle(c);
if(!this.handle){return false
}a(d.iframeFix===true?"iframe":d.iframeFix).each(function(){a("<div class='ui-draggable-iframeFix' style='background: #fff;'></div>").css({width:this.offsetWidth+"px",height:this.offsetHeight+"px",position:"absolute",opacity:"0.001",zIndex:1000}).css(a(this).offset()).appendTo("body")
});return true
},_mouseStart:function(c){var d=this.options;
this.helper=this._createHelper(c);
this.helper.addClass("ui-draggable-dragging");
this._cacheHelperProportions();
if(a.ui.ddmanager){a.ui.ddmanager.current=this
}this._cacheMargins();
this.cssPosition=this.helper.css("position");
this.scrollParent=this.helper.scrollParent();
this.offsetParent=this.helper.offsetParent();
this.offsetParentCssPosition=this.offsetParent.css("position");
this.offset=this.positionAbs=this.element.offset();
this.offset={top:this.offset.top-this.margins.top,left:this.offset.left-this.margins.left};
this.offset.scroll=false;
a.extend(this.offset,{click:{left:c.pageX-this.offset.left,top:c.pageY-this.offset.top},parent:this._getParentOffset(),relative:this._getRelativeOffset()});
this.originalPosition=this.position=this._generatePosition(c);
this.originalPageX=c.pageX;
this.originalPageY=c.pageY;
(d.cursorAt&&this._adjustOffsetFromHelper(d.cursorAt));
this._setContainment();
if(this._trigger("start",c)===false){this._clear();
return false
}this._cacheHelperProportions();
if(a.ui.ddmanager&&!d.dropBehaviour){a.ui.ddmanager.prepareOffsets(this,c)
}this._mouseDrag(c,true);
if(a.ui.ddmanager){a.ui.ddmanager.dragStart(this,c)
}return true
},_mouseDrag:function(c,e){if(this.offsetParentCssPosition==="fixed"){this.offset.parent=this._getParentOffset()
}this.position=this._generatePosition(c);
this.positionAbs=this._convertPositionTo("absolute");
if(!e){var d=this._uiHash();
if(this._trigger("drag",c,d)===false){this._mouseUp({});
return false
}this.position=d.position
}if(!this.options.axis||this.options.axis!=="y"){this.helper[0].style.left=this.position.left+"px"
}if(!this.options.axis||this.options.axis!=="x"){this.helper[0].style.top=this.position.top+"px"
}if(a.ui.ddmanager){a.ui.ddmanager.drag(this,c)
}return false
},_mouseStop:function(d){var c=this,e=false;
if(a.ui.ddmanager&&!this.options.dropBehaviour){e=a.ui.ddmanager.drop(this,d)
}if(this.dropped){e=this.dropped;
this.dropped=false
}if(this.options.helper==="original"&&!a.contains(this.element[0].ownerDocument,this.element[0])){return false
}if((this.options.revert==="invalid"&&!e)||(this.options.revert==="valid"&&e)||this.options.revert===true||(a.isFunction(this.options.revert)&&this.options.revert.call(this.element,e))){a(this.helper).animate(this.originalPosition,parseInt(this.options.revertDuration,10),function(){if(c._trigger("stop",d)!==false){c._clear()
}})}else{if(this._trigger("stop",d)!==false){this._clear()
}}return false
},_mouseUp:function(c){a("div.ui-draggable-iframeFix").each(function(){this.parentNode.removeChild(this)
});if(a.ui.ddmanager){a.ui.ddmanager.dragStop(this,c)
}return a.ui.mouse.prototype._mouseUp.call(this,c)
},cancel:function(){if(this.helper.is(".ui-draggable-dragging")){this._mouseUp({})
}else{this._clear()
}return this
},_getHandle:function(c){return this.options.handle?!!a(c.target).closest(this.element.find(this.options.handle)).length:true
},_createHelper:function(d){var e=this.options,c=a.isFunction(e.helper)?a(e.helper.apply(this.element[0],[d])):(e.helper==="clone"?this.element.clone().removeAttr("id"):this.element);
if(!c.parents("body").length){c.appendTo((e.appendTo==="parent"?this.element[0].parentNode:e.appendTo))
}if(c[0]!==this.element[0]&&!(/(fixed|absolute)/).test(c.css("position"))){c.css("position","absolute")
}return c},_adjustOffsetFromHelper:function(c){if(typeof c==="string"){c=c.split(" ")
}if(a.isArray(c)){c={left:+c[0],top:+c[1]||0}
}if("left" in c){this.offset.click.left=c.left+this.margins.left
}if("right" in c){this.offset.click.left=this.helperProportions.width-c.right+this.margins.left
}if("top" in c){this.offset.click.top=c.top+this.margins.top
}if("bottom" in c){this.offset.click.top=this.helperProportions.height-c.bottom+this.margins.top
}},_getParentOffset:function(){var c=this.offsetParent.offset();
if(this.cssPosition==="absolute"&&this.scrollParent[0]!==document&&a.contains(this.scrollParent[0],this.offsetParent[0])){c.left+=this.scrollParent.scrollLeft();
c.top+=this.scrollParent.scrollTop()
}if((this.offsetParent[0]===document.body)||(this.offsetParent[0].tagName&&this.offsetParent[0].tagName.toLowerCase()==="html"&&a.ui.ie)){c={top:0,left:0}
}return{top:c.top+(parseInt(this.offsetParent.css("borderTopWidth"),10)||0),left:c.left+(parseInt(this.offsetParent.css("borderLeftWidth"),10)||0)}
},_getRelativeOffset:function(){if(this.cssPosition==="relative"){var c=this.element.position();
return{top:c.top-(parseInt(this.helper.css("top"),10)||0)+this.scrollParent.scrollTop(),left:c.left-(parseInt(this.helper.css("left"),10)||0)+this.scrollParent.scrollLeft()}
}else{return{top:0,left:0}
}},_cacheMargins:function(){this.margins={left:(parseInt(this.element.css("marginLeft"),10)||0),top:(parseInt(this.element.css("marginTop"),10)||0),right:(parseInt(this.element.css("marginRight"),10)||0),bottom:(parseInt(this.element.css("marginBottom"),10)||0)}
},_cacheHelperProportions:function(){this.helperProportions={width:this.helper.outerWidth(),height:this.helper.outerHeight()}
},_setContainment:function(){var e,g,d,f=this.options;
if(!f.containment){this.containment=null;
return}if(f.containment==="window"){this.containment=[a(window).scrollLeft()-this.offset.relative.left-this.offset.parent.left,a(window).scrollTop()-this.offset.relative.top-this.offset.parent.top,a(window).scrollLeft()+a(window).width()-this.helperProportions.width-this.margins.left,a(window).scrollTop()+(a(window).height()||document.body.parentNode.scrollHeight)-this.helperProportions.height-this.margins.top];
return}if(f.containment==="document"){this.containment=[0,0,a(document).width()-this.helperProportions.width-this.margins.left,(a(document).height()||document.body.parentNode.scrollHeight)-this.helperProportions.height-this.margins.top];
return}if(f.containment.constructor===Array){this.containment=f.containment;
return}if(f.containment==="parent"){f.containment=this.helper[0].parentNode
}g=a(f.containment);
d=g[0];if(!d){return
}e=g.css("overflow")!=="hidden";
this.containment=[(parseInt(g.css("borderLeftWidth"),10)||0)+(parseInt(g.css("paddingLeft"),10)||0),(parseInt(g.css("borderTopWidth"),10)||0)+(parseInt(g.css("paddingTop"),10)||0),(e?Math.max(d.scrollWidth,d.offsetWidth):d.offsetWidth)-(parseInt(g.css("borderRightWidth"),10)||0)-(parseInt(g.css("paddingRight"),10)||0)-this.helperProportions.width-this.margins.left-this.margins.right,(e?Math.max(d.scrollHeight,d.offsetHeight):d.offsetHeight)-(parseInt(g.css("borderBottomWidth"),10)||0)-(parseInt(g.css("paddingBottom"),10)||0)-this.helperProportions.height-this.margins.top-this.margins.bottom];
this.relative_container=g
},_convertPositionTo:function(f,g){if(!g){g=this.position
}var e=f==="absolute"?1:-1,c=this.cssPosition==="absolute"&&!(this.scrollParent[0]!==document&&a.contains(this.scrollParent[0],this.offsetParent[0]))?this.offsetParent:this.scrollParent;
if(!this.offset.scroll){this.offset.scroll={top:c.scrollTop(),left:c.scrollLeft()}
}return{top:(g.top+this.offset.relative.top*e+this.offset.parent.top*e-((this.cssPosition==="fixed"?-this.scrollParent.scrollTop():this.offset.scroll.top)*e)),left:(g.left+this.offset.relative.left*e+this.offset.parent.left*e-((this.cssPosition==="fixed"?-this.scrollParent.scrollLeft():this.offset.scroll.left)*e))}
},_generatePosition:function(d){var c,i,j,f,e=this.options,k=this.cssPosition==="absolute"&&!(this.scrollParent[0]!==document&&a.contains(this.scrollParent[0],this.offsetParent[0]))?this.offsetParent:this.scrollParent,h=d.pageX,g=d.pageY;
if(!this.offset.scroll){this.offset.scroll={top:k.scrollTop(),left:k.scrollLeft()}
}if(this.originalPosition){if(this.containment){if(this.relative_container){i=this.relative_container.offset();
c=[this.containment[0]+i.left,this.containment[1]+i.top,this.containment[2]+i.left,this.containment[3]+i.top]
}else{c=this.containment
}if(d.pageX-this.offset.click.left<c[0]){h=c[0]+this.offset.click.left
}if(d.pageY-this.offset.click.top<c[1]){g=c[1]+this.offset.click.top
}if(d.pageX-this.offset.click.left>c[2]){h=c[2]+this.offset.click.left
}if(d.pageY-this.offset.click.top>c[3]){g=c[3]+this.offset.click.top
}}if(e.grid){j=e.grid[1]?this.originalPageY+Math.round((g-this.originalPageY)/e.grid[1])*e.grid[1]:this.originalPageY;
g=c?((j-this.offset.click.top>=c[1]||j-this.offset.click.top>c[3])?j:((j-this.offset.click.top>=c[1])?j-e.grid[1]:j+e.grid[1])):j;
f=e.grid[0]?this.originalPageX+Math.round((h-this.originalPageX)/e.grid[0])*e.grid[0]:this.originalPageX;
h=c?((f-this.offset.click.left>=c[0]||f-this.offset.click.left>c[2])?f:((f-this.offset.click.left>=c[0])?f-e.grid[0]:f+e.grid[0])):f
}}return{top:(g-this.offset.click.top-this.offset.relative.top-this.offset.parent.top+(this.cssPosition==="fixed"?-this.scrollParent.scrollTop():this.offset.scroll.top)),left:(h-this.offset.click.left-this.offset.relative.left-this.offset.parent.left+(this.cssPosition==="fixed"?-this.scrollParent.scrollLeft():this.offset.scroll.left))}
},_clear:function(){this.helper.removeClass("ui-draggable-dragging");
if(this.helper[0]!==this.element[0]&&!this.cancelHelperRemoval){this.helper.remove()
}this.helper=null;
this.cancelHelperRemoval=false
},_trigger:function(c,d,e){e=e||this._uiHash();
a.ui.plugin.call(this,c,[d,e]);
if(c==="drag"){this.positionAbs=this._convertPositionTo("absolute")
}return a.Widget.prototype._trigger.call(this,c,d,e)
},plugins:{},_uiHash:function(){return{helper:this.helper,position:this.position,originalPosition:this.originalPosition,offset:this.positionAbs}
}});a.ui.plugin.add("draggable","connectToSortable",{start:function(d,f){var e=a(this).data("ui-draggable"),g=e.options,c=a.extend({},f,{item:e.element});
e.sortables=[];
a(g.connectToSortable).each(function(){var h=a.data(this,"ui-sortable");
if(h&&!h.options.disabled){e.sortables.push({instance:h,shouldRevert:h.options.revert});
h.refreshPositions();
h._trigger("activate",d,c)
}})},stop:function(d,f){var e=a(this).data("ui-draggable"),c=a.extend({},f,{item:e.element});
a.each(e.sortables,function(){if(this.instance.isOver){this.instance.isOver=0;
e.cancelHelperRemoval=true;
this.instance.cancelHelperRemoval=false;
if(this.shouldRevert){this.instance.options.revert=this.shouldRevert
}this.instance._mouseStop(d);
this.instance.options.helper=this.instance.options._helper;
if(e.options.helper==="original"){this.instance.currentItem.css({top:"auto",left:"auto"})
}}else{this.instance.cancelHelperRemoval=false;
this.instance._trigger("deactivate",d,c)
}})},drag:function(d,f){var e=a(this).data("ui-draggable"),c=this;
a.each(e.sortables,function(){var g=false,h=this;
this.instance.positionAbs=e.positionAbs;
this.instance.helperProportions=e.helperProportions;
this.instance.offset.click=e.offset.click;
if(this.instance._intersectsWith(this.instance.containerCache)){g=true;
a.each(e.sortables,function(){this.instance.positionAbs=e.positionAbs;
this.instance.helperProportions=e.helperProportions;
this.instance.offset.click=e.offset.click;
if(this!==h&&this.instance._intersectsWith(this.instance.containerCache)&&a.contains(h.instance.element[0],this.instance.element[0])){g=false
}return g})
}if(g){if(!this.instance.isOver){this.instance.isOver=1;
this.instance.currentItem=a(c).clone().removeAttr("id").appendTo(this.instance.element).data("ui-sortable-item",true);
this.instance.options._helper=this.instance.options.helper;
this.instance.options.helper=function(){return f.helper[0]
};d.target=this.instance.currentItem[0];
this.instance._mouseCapture(d,true);
this.instance._mouseStart(d,true,true);
this.instance.offset.click.top=e.offset.click.top;
this.instance.offset.click.left=e.offset.click.left;
this.instance.offset.parent.left-=e.offset.parent.left-this.instance.offset.parent.left;
this.instance.offset.parent.top-=e.offset.parent.top-this.instance.offset.parent.top;
e._trigger("toSortable",d);
e.dropped=this.instance.element;
e.currentItem=e.element;
this.instance.fromOutside=e
}if(this.instance.currentItem){this.instance._mouseDrag(d)
}}else{if(this.instance.isOver){this.instance.isOver=0;
this.instance.cancelHelperRemoval=true;
this.instance.options.revert=false;
this.instance._trigger("out",d,this.instance._uiHash(this.instance));
this.instance._mouseStop(d,true);
this.instance.options.helper=this.instance.options._helper;
this.instance.currentItem.remove();
if(this.instance.placeholder){this.instance.placeholder.remove()
}e._trigger("fromSortable",d);
e.dropped=false
}}})}});a.ui.plugin.add("draggable","cursor",{start:function(){var c=a("body"),d=a(this).data("ui-draggable").options;
if(c.css("cursor")){d._cursor=c.css("cursor")
}c.css("cursor",d.cursor)
},stop:function(){var c=a(this).data("ui-draggable").options;
if(c._cursor){a("body").css("cursor",c._cursor)
}}});a.ui.plugin.add("draggable","opacity",{start:function(d,e){var c=a(e.helper),f=a(this).data("ui-draggable").options;
if(c.css("opacity")){f._opacity=c.css("opacity")
}c.css("opacity",f.opacity)
},stop:function(c,d){var e=a(this).data("ui-draggable").options;
if(e._opacity){a(d.helper).css("opacity",e._opacity)
}}});a.ui.plugin.add("draggable","scroll",{start:function(){var c=a(this).data("ui-draggable");
if(c.scrollParent[0]!==document&&c.scrollParent[0].tagName!=="HTML"){c.overflowOffset=c.scrollParent.offset()
}},drag:function(e){var d=a(this).data("ui-draggable"),f=d.options,c=false;
if(d.scrollParent[0]!==document&&d.scrollParent[0].tagName!=="HTML"){if(!f.axis||f.axis!=="x"){if((d.overflowOffset.top+d.scrollParent[0].offsetHeight)-e.pageY<f.scrollSensitivity){d.scrollParent[0].scrollTop=c=d.scrollParent[0].scrollTop+f.scrollSpeed
}else{if(e.pageY-d.overflowOffset.top<f.scrollSensitivity){d.scrollParent[0].scrollTop=c=d.scrollParent[0].scrollTop-f.scrollSpeed
}}}if(!f.axis||f.axis!=="y"){if((d.overflowOffset.left+d.scrollParent[0].offsetWidth)-e.pageX<f.scrollSensitivity){d.scrollParent[0].scrollLeft=c=d.scrollParent[0].scrollLeft+f.scrollSpeed
}else{if(e.pageX-d.overflowOffset.left<f.scrollSensitivity){d.scrollParent[0].scrollLeft=c=d.scrollParent[0].scrollLeft-f.scrollSpeed
}}}}else{if(!f.axis||f.axis!=="x"){if(e.pageY-a(document).scrollTop()<f.scrollSensitivity){c=a(document).scrollTop(a(document).scrollTop()-f.scrollSpeed)
}else{if(a(window).height()-(e.pageY-a(document).scrollTop())<f.scrollSensitivity){c=a(document).scrollTop(a(document).scrollTop()+f.scrollSpeed)
}}}if(!f.axis||f.axis!=="y"){if(e.pageX-a(document).scrollLeft()<f.scrollSensitivity){c=a(document).scrollLeft(a(document).scrollLeft()-f.scrollSpeed)
}else{if(a(window).width()-(e.pageX-a(document).scrollLeft())<f.scrollSensitivity){c=a(document).scrollLeft(a(document).scrollLeft()+f.scrollSpeed)
}}}}if(c!==false&&a.ui.ddmanager&&!f.dropBehaviour){a.ui.ddmanager.prepareOffsets(d,e)
}}});a.ui.plugin.add("draggable","snap",{start:function(){var c=a(this).data("ui-draggable"),d=c.options;
c.snapElements=[];
a(d.snap.constructor!==String?(d.snap.items||":data(ui-draggable)"):d.snap).each(function(){var f=a(this),e=f.offset();
if(this!==c.element[0]){c.snapElements.push({item:this,width:f.outerWidth(),height:f.outerHeight(),top:e.top,left:e.left})
}})},drag:function(u,p){var c,z,j,k,s,n,m,A,v,h,g=a(this).data("ui-draggable"),q=g.options,y=q.snapTolerance,x=p.offset.left,w=x+g.helperProportions.width,f=p.offset.top,e=f+g.helperProportions.height;
for(v=g.snapElements.length-1;
v>=0;v--){s=g.snapElements[v].left;
n=s+g.snapElements[v].width;
m=g.snapElements[v].top;
A=m+g.snapElements[v].height;
if(w<s-y||x>n+y||e<m-y||f>A+y||!a.contains(g.snapElements[v].item.ownerDocument,g.snapElements[v].item)){if(g.snapElements[v].snapping){(g.options.snap.release&&g.options.snap.release.call(g.element,u,a.extend(g._uiHash(),{snapItem:g.snapElements[v].item})))
}g.snapElements[v].snapping=false;
continue}if(q.snapMode!=="inner"){c=Math.abs(m-e)<=y;
z=Math.abs(A-f)<=y;
j=Math.abs(s-w)<=y;
k=Math.abs(n-x)<=y;
if(c){p.position.top=g._convertPositionTo("relative",{top:m-g.helperProportions.height,left:0}).top-g.margins.top
}if(z){p.position.top=g._convertPositionTo("relative",{top:A,left:0}).top-g.margins.top
}if(j){p.position.left=g._convertPositionTo("relative",{top:0,left:s-g.helperProportions.width}).left-g.margins.left
}if(k){p.position.left=g._convertPositionTo("relative",{top:0,left:n}).left-g.margins.left
}}h=(c||z||j||k);
if(q.snapMode!=="outer"){c=Math.abs(m-f)<=y;
z=Math.abs(A-e)<=y;
j=Math.abs(s-x)<=y;
k=Math.abs(n-w)<=y;
if(c){p.position.top=g._convertPositionTo("relative",{top:m,left:0}).top-g.margins.top
}if(z){p.position.top=g._convertPositionTo("relative",{top:A-g.helperProportions.height,left:0}).top-g.margins.top
}if(j){p.position.left=g._convertPositionTo("relative",{top:0,left:s}).left-g.margins.left
}if(k){p.position.left=g._convertPositionTo("relative",{top:0,left:n-g.helperProportions.width}).left-g.margins.left
}}if(!g.snapElements[v].snapping&&(c||z||j||k||h)){(g.options.snap.snap&&g.options.snap.snap.call(g.element,u,a.extend(g._uiHash(),{snapItem:g.snapElements[v].item})))
}g.snapElements[v].snapping=(c||z||j||k||h)
}}});a.ui.plugin.add("draggable","stack",{start:function(){var c,e=this.data("ui-draggable").options,d=a.makeArray(a(e.stack)).sort(function(g,f){return(parseInt(a(g).css("zIndex"),10)||0)-(parseInt(a(f).css("zIndex"),10)||0)
});if(!d.length){return
}c=parseInt(a(d[0]).css("zIndex"),10)||0;
a(d).each(function(f){a(this).css("zIndex",c+f)
});this.css("zIndex",(c+d.length))
}});a.ui.plugin.add("draggable","zIndex",{start:function(d,e){var c=a(e.helper),f=a(this).data("ui-draggable").options;
if(c.css("zIndex")){f._zIndex=c.css("zIndex")
}c.css("zIndex",f.zIndex)
},stop:function(c,d){var e=a(this).data("ui-draggable").options;
if(e._zIndex){a(d.helper).css("zIndex",e._zIndex)
}}})})(jQuery);
(function(b,c){function a(e,d,f){return(e>d)&&(e<(d+f))
}b.widget("ui.droppable",{version:"1.10.3",widgetEventPrefix:"drop",options:{accept:"*",activeClass:false,addClasses:true,greedy:false,hoverClass:false,scope:"default",tolerance:"intersect",activate:null,deactivate:null,drop:null,out:null,over:null},_create:function(){var e=this.options,d=e.accept;
this.isover=false;
this.isout=true;
this.accept=b.isFunction(d)?d:function(f){return f.is(d)
};this.proportions={width:this.element[0].offsetWidth,height:this.element[0].offsetHeight};
b.ui.ddmanager.droppables[e.scope]=b.ui.ddmanager.droppables[e.scope]||[];
b.ui.ddmanager.droppables[e.scope].push(this);
(e.addClasses&&this.element.addClass("ui-droppable"))
},_destroy:function(){var e=0,d=b.ui.ddmanager.droppables[this.options.scope];
for(;e<d.length;
e++){if(d[e]===this){d.splice(e,1)
}}this.element.removeClass("ui-droppable ui-droppable-disabled")
},_setOption:function(d,e){if(d==="accept"){this.accept=b.isFunction(e)?e:function(f){return f.is(e)
}}b.Widget.prototype._setOption.apply(this,arguments)
},_activate:function(e){var d=b.ui.ddmanager.current;
if(this.options.activeClass){this.element.addClass(this.options.activeClass)
}if(d){this._trigger("activate",e,this.ui(d))
}},_deactivate:function(e){var d=b.ui.ddmanager.current;
if(this.options.activeClass){this.element.removeClass(this.options.activeClass)
}if(d){this._trigger("deactivate",e,this.ui(d))
}},_over:function(e){var d=b.ui.ddmanager.current;
if(!d||(d.currentItem||d.element)[0]===this.element[0]){return
}if(this.accept.call(this.element[0],(d.currentItem||d.element))){if(this.options.hoverClass){this.element.addClass(this.options.hoverClass)
}this._trigger("over",e,this.ui(d))
}},_out:function(e){var d=b.ui.ddmanager.current;
if(!d||(d.currentItem||d.element)[0]===this.element[0]){return
}if(this.accept.call(this.element[0],(d.currentItem||d.element))){if(this.options.hoverClass){this.element.removeClass(this.options.hoverClass)
}this._trigger("out",e,this.ui(d))
}},_drop:function(e,f){var d=f||b.ui.ddmanager.current,g=false;
if(!d||(d.currentItem||d.element)[0]===this.element[0]){return false
}this.element.find(":data(ui-droppable)").not(".ui-draggable-dragging").each(function(){var h=b.data(this,"ui-droppable");
if(h.options.greedy&&!h.options.disabled&&h.options.scope===d.options.scope&&h.accept.call(h.element[0],(d.currentItem||d.element))&&b.ui.intersect(d,b.extend(h,{offset:h.element.offset()}),h.options.tolerance)){g=true;
return false
}});if(g){return false
}if(this.accept.call(this.element[0],(d.currentItem||d.element))){if(this.options.activeClass){this.element.removeClass(this.options.activeClass)
}if(this.options.hoverClass){this.element.removeClass(this.options.hoverClass)
}this._trigger("drop",e,this.ui(d));
return this.element
}return false
},ui:function(d){return{draggable:(d.currentItem||d.element),helper:d.helper,position:d.position,offset:d.positionAbs}
}});b.ui.intersect=function(q,j,o){if(!j.offset){return false
}var h,i,f=(q.positionAbs||q.position.absolute).left,e=f+q.helperProportions.width,n=(q.positionAbs||q.position.absolute).top,m=n+q.helperProportions.height,g=j.offset.left,d=g+j.proportions.width,p=j.offset.top,k=p+j.proportions.height;
switch(o){case"fit":return(g<=f&&e<=d&&p<=n&&m<=k);
case"intersect":return(g<f+(q.helperProportions.width/2)&&e-(q.helperProportions.width/2)<d&&p<n+(q.helperProportions.height/2)&&m-(q.helperProportions.height/2)<k);
case"pointer":h=((q.positionAbs||q.position.absolute).left+(q.clickOffset||q.offset.click).left);
i=((q.positionAbs||q.position.absolute).top+(q.clickOffset||q.offset.click).top);
return a(i,p,j.proportions.height)&&a(h,g,j.proportions.width);
case"touch":return((n>=p&&n<=k)||(m>=p&&m<=k)||(n<p&&m>k))&&((f>=g&&f<=d)||(e>=g&&e<=d)||(f<g&&e>d));
default:return false
}};b.ui.ddmanager={current:null,droppables:{"default":[]},prepareOffsets:function(g,k){var f,e,d=b.ui.ddmanager.droppables[g.options.scope]||[],h=k?k.type:null,l=(g.currentItem||g.element).find(":data(ui-droppable)").addBack();
droppablesLoop:for(f=0;
f<d.length;
f++){if(d[f].options.disabled||(g&&!d[f].accept.call(d[f].element[0],(g.currentItem||g.element)))){continue
}for(e=0;e<l.length;
e++){if(l[e]===d[f].element[0]){d[f].proportions.height=0;
continue droppablesLoop
}}d[f].visible=d[f].element.css("display")!=="none";
if(!d[f].visible){continue
}if(h==="mousedown"){d[f]._activate.call(d[f],k)
}d[f].offset=d[f].element.offset();
d[f].proportions={width:d[f].element[0].offsetWidth,height:d[f].element[0].offsetHeight}
}},drop:function(d,e){var f=false;
b.each((b.ui.ddmanager.droppables[d.options.scope]||[]).slice(),function(){if(!this.options){return
}if(!this.options.disabled&&this.visible&&b.ui.intersect(d,this,this.options.tolerance)){f=this._drop.call(this,e)||f
}if(!this.options.disabled&&this.visible&&this.accept.call(this.element[0],(d.currentItem||d.element))){this.isout=true;
this.isover=false;
this._deactivate.call(this,e)
}});return f
},dragStart:function(d,e){d.element.parentsUntil("body").bind("scroll.droppable",function(){if(!d.options.refreshPositions){b.ui.ddmanager.prepareOffsets(d,e)
}})},drag:function(d,e){if(d.options.refreshPositions){b.ui.ddmanager.prepareOffsets(d,e)
}b.each(b.ui.ddmanager.droppables[d.options.scope]||[],function(){if(this.options.disabled||this.greedyChild||!this.visible){return
}var i,g,f,h=b.ui.intersect(d,this,this.options.tolerance),j=!h&&this.isover?"isout":(h&&!this.isover?"isover":null);
if(!j){return
}if(this.options.greedy){g=this.options.scope;
f=this.element.parents(":data(ui-droppable)").filter(function(){return b.data(this,"ui-droppable").options.scope===g
});if(f.length){i=b.data(f[0],"ui-droppable");
i.greedyChild=(j==="isover")
}}if(i&&j==="isover"){i.isover=false;
i.isout=true;
i._out.call(i,e)
}this[j]=true;
this[j==="isout"?"isover":"isout"]=false;
this[j==="isover"?"_over":"_out"].call(this,e);
if(i&&j==="isout"){i.isout=false;
i.isover=true;
i._over.call(i,e)
}})},dragStop:function(d,e){d.element.parentsUntil("body").unbind("scroll.droppable");
if(!d.options.refreshPositions){b.ui.ddmanager.prepareOffsets(d,e)
}}}})(jQuery);
(function(c,d){function b(e){return parseInt(e,10)||0
}function a(e){return !isNaN(parseInt(e,10))
}c.widget("ui.resizable",c.ui.mouse,{version:"1.10.3",widgetEventPrefix:"resize",options:{alsoResize:false,animate:false,animateDuration:"slow",animateEasing:"swing",aspectRatio:false,autoHide:false,containment:false,ghost:false,grid:false,handles:"e,s,se",helper:false,maxHeight:null,maxWidth:null,minHeight:10,minWidth:10,zIndex:90,resize:null,start:null,stop:null},_create:function(){var l,f,j,g,e,h=this,k=this.options;
this.element.addClass("ui-resizable");
c.extend(this,{_aspectRatio:!!(k.aspectRatio),aspectRatio:k.aspectRatio,originalElement:this.element,_proportionallyResizeElements:[],_helper:k.helper||k.ghost||k.animate?k.helper||"ui-resizable-helper":null});
if(this.element[0].nodeName.match(/canvas|textarea|input|select|button|img/i)){this.element.wrap(c("<div class='ui-wrapper' style='overflow: hidden;'></div>").css({position:this.element.css("position"),width:this.element.outerWidth(),height:this.element.outerHeight(),top:this.element.css("top"),left:this.element.css("left")}));
this.element=this.element.parent().data("ui-resizable",this.element.data("ui-resizable"));
this.elementIsWrapper=true;
this.element.css({marginLeft:this.originalElement.css("marginLeft"),marginTop:this.originalElement.css("marginTop"),marginRight:this.originalElement.css("marginRight"),marginBottom:this.originalElement.css("marginBottom")});
this.originalElement.css({marginLeft:0,marginTop:0,marginRight:0,marginBottom:0});
this.originalResizeStyle=this.originalElement.css("resize");
this.originalElement.css("resize","none");
this._proportionallyResizeElements.push(this.originalElement.css({position:"static",zoom:1,display:"block"}));
this.originalElement.css({margin:this.originalElement.css("margin")});
this._proportionallyResize()
}this.handles=k.handles||(!c(".ui-resizable-handle",this.element).length?"e,s,se":{n:".ui-resizable-n",e:".ui-resizable-e",s:".ui-resizable-s",w:".ui-resizable-w",se:".ui-resizable-se",sw:".ui-resizable-sw",ne:".ui-resizable-ne",nw:".ui-resizable-nw"});
if(this.handles.constructor===String){if(this.handles==="all"){this.handles="n,e,s,w,se,sw,ne,nw"
}l=this.handles.split(",");
this.handles={};
for(f=0;f<l.length;
f++){j=c.trim(l[f]);
e="ui-resizable-"+j;
g=c("<div class='ui-resizable-handle "+e+"'></div>");
g.css({zIndex:k.zIndex});
if("se"===j){g.addClass("ui-icon ui-icon-gripsmall-diagonal-se")
}this.handles[j]=".ui-resizable-"+j;
this.element.append(g)
}}this._renderAxis=function(q){var n,o,m,p;
q=q||this.element;
for(n in this.handles){if(this.handles[n].constructor===String){this.handles[n]=c(this.handles[n],this.element).show()
}if(this.elementIsWrapper&&this.originalElement[0].nodeName.match(/textarea|input|select|button/i)){o=c(this.handles[n],this.element);
p=/sw|ne|nw|se|n|s/.test(n)?o.outerHeight():o.outerWidth();
m=["padding",/ne|nw|n/.test(n)?"Top":/se|sw|s/.test(n)?"Bottom":/^e$/.test(n)?"Right":"Left"].join("");
q.css(m,p);
this._proportionallyResize()
}if(!c(this.handles[n]).length){continue
}}};this._renderAxis(this.element);
this._handles=c(".ui-resizable-handle",this.element).disableSelection();
this._handles.mouseover(function(){if(!h.resizing){if(this.className){g=this.className.match(/ui-resizable-(se|sw|ne|nw|n|e|s|w)/i)
}h.axis=g&&g[1]?g[1]:"se"
}});if(k.autoHide){this._handles.hide();
c(this.element).addClass("ui-resizable-autohide").mouseenter(function(){if(k.disabled){return
}c(this).removeClass("ui-resizable-autohide");
h._handles.show()
}).mouseleave(function(){if(k.disabled){return
}if(!h.resizing){c(this).addClass("ui-resizable-autohide");
h._handles.hide()
}})}this._mouseInit()
},_destroy:function(){this._mouseDestroy();
var f,e=function(g){c(g).removeClass("ui-resizable ui-resizable-disabled ui-resizable-resizing").removeData("resizable").removeData("ui-resizable").unbind(".resizable").find(".ui-resizable-handle").remove()
};if(this.elementIsWrapper){e(this.element);
f=this.element;
this.originalElement.css({position:f.css("position"),width:f.outerWidth(),height:f.outerHeight(),top:f.css("top"),left:f.css("left")}).insertAfter(f);
f.remove()}this.originalElement.css("resize",this.originalResizeStyle);
e(this.originalElement);
return this
},_mouseCapture:function(g){var f,h,e=false;
for(f in this.handles){h=c(this.handles[f])[0];
if(h===g.target||c.contains(h,g.target)){e=true
}}return !this.options.disabled&&e
},_mouseStart:function(g){var k,h,j,i=this.options,f=this.element.position(),e=this.element;
this.resizing=true;
if((/absolute/).test(e.css("position"))){e.css({position:"absolute",top:e.css("top"),left:e.css("left")})
}else{if(e.is(".ui-draggable")){e.css({position:"absolute",top:f.top,left:f.left})
}}this._renderProxy();
k=b(this.helper.css("left"));
h=b(this.helper.css("top"));
if(i.containment){k+=c(i.containment).scrollLeft()||0;
h+=c(i.containment).scrollTop()||0
}this.offset=this.helper.offset();
this.position={left:k,top:h};
this.size=this._helper?{width:e.outerWidth(),height:e.outerHeight()}:{width:e.width(),height:e.height()};
this.originalSize=this._helper?{width:e.outerWidth(),height:e.outerHeight()}:{width:e.width(),height:e.height()};
this.originalPosition={left:k,top:h};
this.sizeDiff={width:e.outerWidth()-e.width(),height:e.outerHeight()-e.height()};
this.originalMousePosition={left:g.pageX,top:g.pageY};
this.aspectRatio=(typeof i.aspectRatio==="number")?i.aspectRatio:((this.originalSize.width/this.originalSize.height)||1);
j=c(".ui-resizable-"+this.axis).css("cursor");
c("body").css("cursor",j==="auto"?this.axis+"-resize":j);
e.addClass("ui-resizable-resizing");
this._propagate("start",g);
return true
},_mouseDrag:function(e){var k,g=this.helper,l={},i=this.originalMousePosition,m=this.axis,o=this.position.top,f=this.position.left,n=this.size.width,j=this.size.height,q=(e.pageX-i.left)||0,p=(e.pageY-i.top)||0,h=this._change[m];
if(!h){return false
}k=h.apply(this,[e,q,p]);
this._updateVirtualBoundaries(e.shiftKey);
if(this._aspectRatio||e.shiftKey){k=this._updateRatio(k,e)
}k=this._respectSize(k,e);
this._updateCache(k);
this._propagate("resize",e);
if(this.position.top!==o){l.top=this.position.top+"px"
}if(this.position.left!==f){l.left=this.position.left+"px"
}if(this.size.width!==n){l.width=this.size.width+"px"
}if(this.size.height!==j){l.height=this.size.height+"px"
}g.css(l);if(!this._helper&&this._proportionallyResizeElements.length){this._proportionallyResize()
}if(!c.isEmptyObject(l)){this._trigger("resize",e,this.ui())
}return false
},_mouseStop:function(h){this.resizing=false;
var g,e,f,k,n,j,m,i=this.options,l=this;
if(this._helper){g=this._proportionallyResizeElements;
e=g.length&&(/textarea/i).test(g[0].nodeName);
f=e&&c.ui.hasScroll(g[0],"left")?0:l.sizeDiff.height;
k=e?0:l.sizeDiff.width;
n={width:(l.helper.width()-k),height:(l.helper.height()-f)};
j=(parseInt(l.element.css("left"),10)+(l.position.left-l.originalPosition.left))||null;
m=(parseInt(l.element.css("top"),10)+(l.position.top-l.originalPosition.top))||null;
if(!i.animate){this.element.css(c.extend(n,{top:m,left:j}))
}l.helper.height(l.size.height);
l.helper.width(l.size.width);
if(this._helper&&!i.animate){this._proportionallyResize()
}}c("body").css("cursor","auto");
this.element.removeClass("ui-resizable-resizing");
this._propagate("stop",h);
if(this._helper){this.helper.remove()
}return false
},_updateVirtualBoundaries:function(g){var i,h,f,k,e,j=this.options;
e={minWidth:a(j.minWidth)?j.minWidth:0,maxWidth:a(j.maxWidth)?j.maxWidth:Infinity,minHeight:a(j.minHeight)?j.minHeight:0,maxHeight:a(j.maxHeight)?j.maxHeight:Infinity};
if(this._aspectRatio||g){i=e.minHeight*this.aspectRatio;
f=e.minWidth/this.aspectRatio;
h=e.maxHeight*this.aspectRatio;
k=e.maxWidth/this.aspectRatio;
if(i>e.minWidth){e.minWidth=i
}if(f>e.minHeight){e.minHeight=f
}if(h<e.maxWidth){e.maxWidth=h
}if(k<e.maxHeight){e.maxHeight=k
}}this._vBoundaries=e
},_updateCache:function(e){this.offset=this.helper.offset();
if(a(e.left)){this.position.left=e.left
}if(a(e.top)){this.position.top=e.top
}if(a(e.height)){this.size.height=e.height
}if(a(e.width)){this.size.width=e.width
}},_updateRatio:function(g){var h=this.position,f=this.size,e=this.axis;
if(a(g.height)){g.width=(g.height*this.aspectRatio)
}else{if(a(g.width)){g.height=(g.width/this.aspectRatio)
}}if(e==="sw"){g.left=h.left+(f.width-g.width);
g.top=null}if(e==="nw"){g.top=h.top+(f.height-g.height);
g.left=h.left+(f.width-g.width)
}return g},_respectSize:function(j){var g=this._vBoundaries,m=this.axis,p=a(j.width)&&g.maxWidth&&(g.maxWidth<j.width),k=a(j.height)&&g.maxHeight&&(g.maxHeight<j.height),h=a(j.width)&&g.minWidth&&(g.minWidth>j.width),n=a(j.height)&&g.minHeight&&(g.minHeight>j.height),f=this.originalPosition.left+this.originalSize.width,l=this.position.top+this.size.height,i=/sw|nw|w/.test(m),e=/nw|ne|n/.test(m);
if(h){j.width=g.minWidth
}if(n){j.height=g.minHeight
}if(p){j.width=g.maxWidth
}if(k){j.height=g.maxHeight
}if(h&&i){j.left=f-g.minWidth
}if(p&&i){j.left=f-g.maxWidth
}if(n&&e){j.top=l-g.minHeight
}if(k&&e){j.top=l-g.maxHeight
}if(!j.width&&!j.height&&!j.left&&j.top){j.top=null
}else{if(!j.width&&!j.height&&!j.top&&j.left){j.left=null
}}return j},_proportionallyResize:function(){if(!this._proportionallyResizeElements.length){return
}var h,f,l,e,k,g=this.helper||this.element;
for(h=0;h<this._proportionallyResizeElements.length;
h++){k=this._proportionallyResizeElements[h];
if(!this.borderDif){this.borderDif=[];
l=[k.css("borderTopWidth"),k.css("borderRightWidth"),k.css("borderBottomWidth"),k.css("borderLeftWidth")];
e=[k.css("paddingTop"),k.css("paddingRight"),k.css("paddingBottom"),k.css("paddingLeft")];
for(f=0;f<l.length;
f++){this.borderDif[f]=(parseInt(l[f],10)||0)+(parseInt(e[f],10)||0)
}}k.css({height:(g.height()-this.borderDif[0]-this.borderDif[2])||0,width:(g.width()-this.borderDif[1]-this.borderDif[3])||0})
}},_renderProxy:function(){var e=this.element,f=this.options;
this.elementOffset=e.offset();
if(this._helper){this.helper=this.helper||c("<div style='overflow:hidden;'></div>");
this.helper.addClass(this._helper).css({width:this.element.outerWidth()-1,height:this.element.outerHeight()-1,position:"absolute",left:this.elementOffset.left+"px",top:this.elementOffset.top+"px",zIndex:++f.zIndex});
this.helper.appendTo("body").disableSelection()
}else{this.helper=this.element
}},_change:{e:function(f,e){return{width:this.originalSize.width+e}
},w:function(g,e){var f=this.originalSize,h=this.originalPosition;
return{left:h.left+e,width:f.width-e}
},n:function(h,f,e){var g=this.originalSize,i=this.originalPosition;
return{top:i.top+e,height:g.height-e}
},s:function(g,f,e){return{height:this.originalSize.height+e}
},se:function(g,f,e){return c.extend(this._change.s.apply(this,arguments),this._change.e.apply(this,[g,f,e]))
},sw:function(g,f,e){return c.extend(this._change.s.apply(this,arguments),this._change.w.apply(this,[g,f,e]))
},ne:function(g,f,e){return c.extend(this._change.n.apply(this,arguments),this._change.e.apply(this,[g,f,e]))
},nw:function(g,f,e){return c.extend(this._change.n.apply(this,arguments),this._change.w.apply(this,[g,f,e]))
}},_propagate:function(f,e){c.ui.plugin.call(this,f,[e,this.ui()]);
(f!=="resize"&&this._trigger(f,e,this.ui()))
},plugins:{},ui:function(){return{originalElement:this.originalElement,element:this.element,helper:this.helper,position:this.position,size:this.size,originalSize:this.originalSize,originalPosition:this.originalPosition}
}});c.ui.plugin.add("resizable","animate",{stop:function(h){var m=c(this).data("ui-resizable"),j=m.options,g=m._proportionallyResizeElements,e=g.length&&(/textarea/i).test(g[0].nodeName),f=e&&c.ui.hasScroll(g[0],"left")?0:m.sizeDiff.height,l=e?0:m.sizeDiff.width,i={width:(m.size.width-l),height:(m.size.height-f)},k=(parseInt(m.element.css("left"),10)+(m.position.left-m.originalPosition.left))||null,n=(parseInt(m.element.css("top"),10)+(m.position.top-m.originalPosition.top))||null;
m.element.animate(c.extend(i,n&&k?{top:n,left:k}:{}),{duration:j.animateDuration,easing:j.animateEasing,step:function(){var o={width:parseInt(m.element.css("width"),10),height:parseInt(m.element.css("height"),10),top:parseInt(m.element.css("top"),10),left:parseInt(m.element.css("left"),10)};
if(g&&g.length){c(g[0]).css({width:o.width,height:o.height})
}m._updateCache(o);
m._propagate("resize",h)
}})}});c.ui.plugin.add("resizable","containment",{start:function(){var m,g,q,e,l,h,r,n=c(this).data("ui-resizable"),k=n.options,j=n.element,f=k.containment,i=(f instanceof c)?f.get(0):(/parent/.test(f))?j.parent().get(0):f;
if(!i){return
}n.containerElement=c(i);
if(/document/.test(f)||f===document){n.containerOffset={left:0,top:0};
n.containerPosition={left:0,top:0};
n.parentData={element:c(document),left:0,top:0,width:c(document).width(),height:c(document).height()||document.body.parentNode.scrollHeight}
}else{m=c(i);
g=[];c(["Top","Right","Left","Bottom"]).each(function(p,o){g[p]=b(m.css("padding"+o))
});n.containerOffset=m.offset();
n.containerPosition=m.position();
n.containerSize={height:(m.innerHeight()-g[3]),width:(m.innerWidth()-g[1])};
q=n.containerOffset;
e=n.containerSize.height;
l=n.containerSize.width;
h=(c.ui.hasScroll(i,"left")?i.scrollWidth:l);
r=(c.ui.hasScroll(i)?i.scrollHeight:e);
n.parentData={element:i,left:q.left,top:q.top,width:h,height:r}
}},resize:function(f){var k,q,j,i,l=c(this).data("ui-resizable"),h=l.options,n=l.containerOffset,m=l.position,p=l._aspectRatio||f.shiftKey,e={top:0,left:0},g=l.containerElement;
if(g[0]!==document&&(/static/).test(g.css("position"))){e=n
}if(m.left<(l._helper?n.left:0)){l.size.width=l.size.width+(l._helper?(l.position.left-n.left):(l.position.left-e.left));
if(p){l.size.height=l.size.width/l.aspectRatio
}l.position.left=h.helper?n.left:0
}if(m.top<(l._helper?n.top:0)){l.size.height=l.size.height+(l._helper?(l.position.top-n.top):l.position.top);
if(p){l.size.width=l.size.height*l.aspectRatio
}l.position.top=l._helper?n.top:0
}l.offset.left=l.parentData.left+l.position.left;
l.offset.top=l.parentData.top+l.position.top;
k=Math.abs((l._helper?l.offset.left-e.left:(l.offset.left-e.left))+l.sizeDiff.width);
q=Math.abs((l._helper?l.offset.top-e.top:(l.offset.top-n.top))+l.sizeDiff.height);
j=l.containerElement.get(0)===l.element.parent().get(0);
i=/relative|absolute/.test(l.containerElement.css("position"));
if(j&&i){k-=l.parentData.left
}if(k+l.size.width>=l.parentData.width){l.size.width=l.parentData.width-k;
if(p){l.size.height=l.size.width/l.aspectRatio
}}if(q+l.size.height>=l.parentData.height){l.size.height=l.parentData.height-q;
if(p){l.size.width=l.size.height*l.aspectRatio
}}},stop:function(){var k=c(this).data("ui-resizable"),f=k.options,l=k.containerOffset,e=k.containerPosition,g=k.containerElement,i=c(k.helper),n=i.offset(),m=i.outerWidth()-k.sizeDiff.width,j=i.outerHeight()-k.sizeDiff.height;
if(k._helper&&!f.animate&&(/relative/).test(g.css("position"))){c(this).css({left:n.left-e.left-l.left,width:m,height:j})
}if(k._helper&&!f.animate&&(/static/).test(g.css("position"))){c(this).css({left:n.left-e.left-l.left,width:m,height:j})
}}});c.ui.plugin.add("resizable","alsoResize",{start:function(){var e=c(this).data("ui-resizable"),g=e.options,f=function(h){c(h).each(function(){var i=c(this);
i.data("ui-resizable-alsoresize",{width:parseInt(i.width(),10),height:parseInt(i.height(),10),left:parseInt(i.css("left"),10),top:parseInt(i.css("top"),10)})
})};if(typeof(g.alsoResize)==="object"&&!g.alsoResize.parentNode){if(g.alsoResize.length){g.alsoResize=g.alsoResize[0];
f(g.alsoResize)
}else{c.each(g.alsoResize,function(h){f(h)
})}}else{f(g.alsoResize)
}},resize:function(g,i){var f=c(this).data("ui-resizable"),j=f.options,h=f.originalSize,l=f.originalPosition,k={height:(f.size.height-h.height)||0,width:(f.size.width-h.width)||0,top:(f.position.top-l.top)||0,left:(f.position.left-l.left)||0},e=function(m,n){c(m).each(function(){var q=c(this),r=c(this).data("ui-resizable-alsoresize"),p={},o=n&&n.length?n:q.parents(i.originalElement[0]).length?["width","height"]:["width","height","top","left"];
c.each(o,function(s,u){var t=(r[u]||0)+(k[u]||0);
if(t&&t>=0){p[u]=t||null
}});q.css(p)
})};if(typeof(j.alsoResize)==="object"&&!j.alsoResize.nodeType){c.each(j.alsoResize,function(m,n){e(m,n)
})}else{e(j.alsoResize)
}},stop:function(){c(this).removeData("resizable-alsoresize")
}});c.ui.plugin.add("resizable","ghost",{start:function(){var f=c(this).data("ui-resizable"),g=f.options,e=f.size;
f.ghost=f.originalElement.clone();
f.ghost.css({opacity:0.25,display:"block",position:"relative",height:e.height,width:e.width,margin:0,left:0,top:0}).addClass("ui-resizable-ghost").addClass(typeof g.ghost==="string"?g.ghost:"");
f.ghost.appendTo(f.helper)
},resize:function(){var e=c(this).data("ui-resizable");
if(e.ghost){e.ghost.css({position:"relative",height:e.size.height,width:e.size.width})
}},stop:function(){var e=c(this).data("ui-resizable");
if(e.ghost&&e.helper){e.helper.get(0).removeChild(e.ghost.get(0))
}}});c.ui.plugin.add("resizable","grid",{resize:function(){var r=c(this).data("ui-resizable"),i=r.options,s=r.size,k=r.originalSize,n=r.originalPosition,t=r.axis,f=typeof i.grid==="number"?[i.grid,i.grid]:i.grid,p=(f[0]||1),m=(f[1]||1),h=Math.round((s.width-k.width)/p)*p,g=Math.round((s.height-k.height)/m)*m,l=k.width+h,e=k.height+g,j=i.maxWidth&&(i.maxWidth<l),u=i.maxHeight&&(i.maxHeight<e),q=i.minWidth&&(i.minWidth>l),v=i.minHeight&&(i.minHeight>e);
i.grid=f;if(q){l=l+p
}if(v){e=e+m
}if(j){l=l-p
}if(u){e=e-m
}if(/^(se|s|e)$/.test(t)){r.size.width=l;
r.size.height=e
}else{if(/^(ne)$/.test(t)){r.size.width=l;
r.size.height=e;
r.position.top=n.top-g
}else{if(/^(sw)$/.test(t)){r.size.width=l;
r.size.height=e;
r.position.left=n.left-h
}else{r.size.width=l;
r.size.height=e;
r.position.top=n.top-g;
r.position.left=n.left-h
}}}}})})(jQuery);
(function(a,b){a.widget("ui.selectable",a.ui.mouse,{version:"1.10.3",options:{appendTo:"body",autoRefresh:true,distance:0,filter:"*",tolerance:"touch",selected:null,selecting:null,start:null,stop:null,unselected:null,unselecting:null},_create:function(){var d,c=this;
this.element.addClass("ui-selectable");
this.dragged=false;
this.refresh=function(){d=a(c.options.filter,c.element[0]);
d.addClass("ui-selectee");
d.each(function(){var e=a(this),f=e.offset();
a.data(this,"selectable-item",{element:this,$element:e,left:f.left,top:f.top,right:f.left+e.outerWidth(),bottom:f.top+e.outerHeight(),startselected:false,selected:e.hasClass("ui-selected"),selecting:e.hasClass("ui-selecting"),unselecting:e.hasClass("ui-unselecting")})
})};this.refresh();
this.selectees=d.addClass("ui-selectee");
this._mouseInit();
this.helper=a("<div class='ui-selectable-helper'></div>")
},_destroy:function(){this.selectees.removeClass("ui-selectee").removeData("selectable-item");
this.element.removeClass("ui-selectable ui-selectable-disabled");
this._mouseDestroy()
},_mouseStart:function(e){var d=this,c=this.options;
this.opos=[e.pageX,e.pageY];
if(this.options.disabled){return
}this.selectees=a(c.filter,this.element[0]);
this._trigger("start",e);
a(c.appendTo).append(this.helper);
this.helper.css({left:e.pageX,top:e.pageY,width:0,height:0});
if(c.autoRefresh){this.refresh()
}this.selectees.filter(".ui-selected").each(function(){var f=a.data(this,"selectable-item");
f.startselected=true;
if(!e.metaKey&&!e.ctrlKey){f.$element.removeClass("ui-selected");
f.selected=false;
f.$element.addClass("ui-unselecting");
f.unselecting=true;
d._trigger("unselecting",e,{unselecting:f.element})
}});a(e.target).parents().addBack().each(function(){var f,g=a.data(this,"selectable-item");
if(g){f=(!e.metaKey&&!e.ctrlKey)||!g.$element.hasClass("ui-selected");
g.$element.removeClass(f?"ui-unselecting":"ui-selected").addClass(f?"ui-selecting":"ui-unselecting");
g.unselecting=!f;
g.selecting=f;
g.selected=f;
if(f){d._trigger("selecting",e,{selecting:g.element})
}else{d._trigger("unselecting",e,{unselecting:g.element})
}return false
}})},_mouseDrag:function(j){this.dragged=true;
if(this.options.disabled){return
}var g,i=this,e=this.options,d=this.opos[0],h=this.opos[1],c=j.pageX,f=j.pageY;
if(d>c){g=c;
c=d;d=g}if(h>f){g=f;
f=h;h=g}this.helper.css({left:d,top:h,width:c-d,height:f-h});
this.selectees.each(function(){var k=a.data(this,"selectable-item"),l=false;
if(!k||k.element===i.element[0]){return
}if(e.tolerance==="touch"){l=(!(k.left>c||k.right<d||k.top>f||k.bottom<h))
}else{if(e.tolerance==="fit"){l=(k.left>d&&k.right<c&&k.top>h&&k.bottom<f)
}}if(l){if(k.selected){k.$element.removeClass("ui-selected");
k.selected=false
}if(k.unselecting){k.$element.removeClass("ui-unselecting");
k.unselecting=false
}if(!k.selecting){k.$element.addClass("ui-selecting");
k.selecting=true;
i._trigger("selecting",j,{selecting:k.element})
}}else{if(k.selecting){if((j.metaKey||j.ctrlKey)&&k.startselected){k.$element.removeClass("ui-selecting");
k.selecting=false;
k.$element.addClass("ui-selected");
k.selected=true
}else{k.$element.removeClass("ui-selecting");
k.selecting=false;
if(k.startselected){k.$element.addClass("ui-unselecting");
k.unselecting=true
}i._trigger("unselecting",j,{unselecting:k.element})
}}if(k.selected){if(!j.metaKey&&!j.ctrlKey&&!k.startselected){k.$element.removeClass("ui-selected");
k.selected=false;
k.$element.addClass("ui-unselecting");
k.unselecting=true;
i._trigger("unselecting",j,{unselecting:k.element})
}}}});return false
},_mouseStop:function(d){var c=this;
this.dragged=false;
a(".ui-unselecting",this.element[0]).each(function(){var e=a.data(this,"selectable-item");
e.$element.removeClass("ui-unselecting");
e.unselecting=false;
e.startselected=false;
c._trigger("unselected",d,{unselected:e.element})
});a(".ui-selecting",this.element[0]).each(function(){var e=a.data(this,"selectable-item");
e.$element.removeClass("ui-selecting").addClass("ui-selected");
e.selecting=false;
e.selected=true;
e.startselected=true;
c._trigger("selected",d,{selected:e.element})
});this._trigger("stop",d);
this.helper.remove();
return false
}})})(jQuery);
(function(b,d){function a(f,e,g){return(f>e)&&(f<(e+g))
}function c(e){return(/left|right/).test(e.css("float"))||(/inline|table-cell/).test(e.css("display"))
}b.widget("ui.sortable",b.ui.mouse,{version:"1.10.3",widgetEventPrefix:"sort",ready:false,options:{appendTo:"parent",axis:false,connectWith:false,containment:false,cursor:"auto",cursorAt:false,dropOnEmpty:true,forcePlaceholderSize:false,forceHelperSize:false,grid:false,handle:false,helper:"original",items:"> *",opacity:false,placeholder:false,revert:false,scroll:true,scrollSensitivity:20,scrollSpeed:20,scope:"default",tolerance:"intersect",zIndex:1000,activate:null,beforeStop:null,change:null,deactivate:null,out:null,over:null,receive:null,remove:null,sort:null,start:null,stop:null,update:null},_create:function(){var e=this.options;
this.containerCache={};
this.element.addClass("ui-sortable");
this.refresh();
this.floating=this.items.length?e.axis==="x"||c(this.items[0].item):false;
this.offset=this.element.offset();
this._mouseInit();
this.ready=true
},_destroy:function(){this.element.removeClass("ui-sortable ui-sortable-disabled");
this._mouseDestroy();
for(var e=this.items.length-1;
e>=0;e--){this.items[e].item.removeData(this.widgetName+"-item")
}return this
},_setOption:function(e,f){if(e==="disabled"){this.options[e]=f;
this.widget().toggleClass("ui-sortable-disabled",!!f)
}else{b.Widget.prototype._setOption.apply(this,arguments)
}},_mouseCapture:function(g,h){var e=null,i=false,f=this;
if(this.reverting){return false
}if(this.options.disabled||this.options.type==="static"){return false
}this._refreshItems(g);
b(g.target).parents().each(function(){if(b.data(this,f.widgetName+"-item")===f){e=b(this);
return false
}});if(b.data(g.target,f.widgetName+"-item")===f){e=b(g.target)
}if(!e){return false
}if(this.options.handle&&!h){b(this.options.handle,e).find("*").addBack().each(function(){if(this===g.target){i=true
}});if(!i){return false
}}this.currentItem=e;
this._removeCurrentsFromItems();
return true
},_mouseStart:function(h,j,f){var g,e,k=this.options;
this.currentContainer=this;
this.refreshPositions();
this.helper=this._createHelper(h);
this._cacheHelperProportions();
this._cacheMargins();
this.scrollParent=this.helper.scrollParent();
this.offset=this.currentItem.offset();
this.offset={top:this.offset.top-this.margins.top,left:this.offset.left-this.margins.left};
b.extend(this.offset,{click:{left:h.pageX-this.offset.left,top:h.pageY-this.offset.top},parent:this._getParentOffset(),relative:this._getRelativeOffset()});
this.helper.css("position","absolute");
this.cssPosition=this.helper.css("position");
this.originalPosition=this._generatePosition(h);
this.originalPageX=h.pageX;
this.originalPageY=h.pageY;
(k.cursorAt&&this._adjustOffsetFromHelper(k.cursorAt));
this.domPosition={prev:this.currentItem.prev()[0],parent:this.currentItem.parent()[0]};
if(this.helper[0]!==this.currentItem[0]){this.currentItem.hide()
}this._createPlaceholder();
if(k.containment){this._setContainment()
}if(k.cursor&&k.cursor!=="auto"){e=this.document.find("body");
this.storedCursor=e.css("cursor");
e.css("cursor",k.cursor);
this.storedStylesheet=b("<style>*{ cursor: "+k.cursor+" !important; }</style>").appendTo(e)
}if(k.opacity){if(this.helper.css("opacity")){this._storedOpacity=this.helper.css("opacity")
}this.helper.css("opacity",k.opacity)
}if(k.zIndex){if(this.helper.css("zIndex")){this._storedZIndex=this.helper.css("zIndex")
}this.helper.css("zIndex",k.zIndex)
}if(this.scrollParent[0]!==document&&this.scrollParent[0].tagName!=="HTML"){this.overflowOffset=this.scrollParent.offset()
}this._trigger("start",h,this._uiHash());
if(!this._preserveHelperProportions){this._cacheHelperProportions()
}if(!f){for(g=this.containers.length-1;
g>=0;g--){this.containers[g]._trigger("activate",h,this._uiHash(this))
}}if(b.ui.ddmanager){b.ui.ddmanager.current=this
}if(b.ui.ddmanager&&!k.dropBehaviour){b.ui.ddmanager.prepareOffsets(this,h)
}this.dragging=true;
this.helper.addClass("ui-sortable-helper");
this._mouseDrag(h);
return true
},_mouseDrag:function(j){var g,h,f,l,k=this.options,e=false;
this.position=this._generatePosition(j);
this.positionAbs=this._convertPositionTo("absolute");
if(!this.lastPositionAbs){this.lastPositionAbs=this.positionAbs
}if(this.options.scroll){if(this.scrollParent[0]!==document&&this.scrollParent[0].tagName!=="HTML"){if((this.overflowOffset.top+this.scrollParent[0].offsetHeight)-j.pageY<k.scrollSensitivity){this.scrollParent[0].scrollTop=e=this.scrollParent[0].scrollTop+k.scrollSpeed
}else{if(j.pageY-this.overflowOffset.top<k.scrollSensitivity){this.scrollParent[0].scrollTop=e=this.scrollParent[0].scrollTop-k.scrollSpeed
}}if((this.overflowOffset.left+this.scrollParent[0].offsetWidth)-j.pageX<k.scrollSensitivity){this.scrollParent[0].scrollLeft=e=this.scrollParent[0].scrollLeft+k.scrollSpeed
}else{if(j.pageX-this.overflowOffset.left<k.scrollSensitivity){this.scrollParent[0].scrollLeft=e=this.scrollParent[0].scrollLeft-k.scrollSpeed
}}}else{if(j.pageY-b(document).scrollTop()<k.scrollSensitivity){e=b(document).scrollTop(b(document).scrollTop()-k.scrollSpeed)
}else{if(b(window).height()-(j.pageY-b(document).scrollTop())<k.scrollSensitivity){e=b(document).scrollTop(b(document).scrollTop()+k.scrollSpeed)
}}if(j.pageX-b(document).scrollLeft()<k.scrollSensitivity){e=b(document).scrollLeft(b(document).scrollLeft()-k.scrollSpeed)
}else{if(b(window).width()-(j.pageX-b(document).scrollLeft())<k.scrollSensitivity){e=b(document).scrollLeft(b(document).scrollLeft()+k.scrollSpeed)
}}}if(e!==false&&b.ui.ddmanager&&!k.dropBehaviour){b.ui.ddmanager.prepareOffsets(this,j)
}}this.positionAbs=this._convertPositionTo("absolute");
if(!this.options.axis||this.options.axis!=="y"){this.helper[0].style.left=this.position.left+"px"
}if(!this.options.axis||this.options.axis!=="x"){this.helper[0].style.top=this.position.top+"px"
}for(g=this.items.length-1;
g>=0;g--){h=this.items[g];
f=h.item[0];
l=this._intersectsWithPointer(h);
if(!l){continue
}if(h.instance!==this.currentContainer){continue
}if(f!==this.currentItem[0]&&this.placeholder[l===1?"next":"prev"]()[0]!==f&&!b.contains(this.placeholder[0],f)&&(this.options.type==="semi-dynamic"?!b.contains(this.element[0],f):true)){this.direction=l===1?"down":"up";
if(this.options.tolerance==="pointer"||this._intersectsWithSides(h)){this._rearrange(j,h)
}else{break
}this._trigger("change",j,this._uiHash());
break}}this._contactContainers(j);
if(b.ui.ddmanager){b.ui.ddmanager.drag(this,j)
}this._trigger("sort",j,this._uiHash());
this.lastPositionAbs=this.positionAbs;
return false
},_mouseStop:function(g,i){if(!g){return
}if(b.ui.ddmanager&&!this.options.dropBehaviour){b.ui.ddmanager.drop(this,g)
}if(this.options.revert){var f=this,j=this.placeholder.offset(),e=this.options.axis,h={};
if(!e||e==="x"){h.left=j.left-this.offset.parent.left-this.margins.left+(this.offsetParent[0]===document.body?0:this.offsetParent[0].scrollLeft)
}if(!e||e==="y"){h.top=j.top-this.offset.parent.top-this.margins.top+(this.offsetParent[0]===document.body?0:this.offsetParent[0].scrollTop)
}this.reverting=true;
b(this.helper).animate(h,parseInt(this.options.revert,10)||500,function(){f._clear(g)
})}else{this._clear(g,i)
}return false
},cancel:function(){if(this.dragging){this._mouseUp({target:null});
if(this.options.helper==="original"){this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper")
}else{this.currentItem.show()
}for(var e=this.containers.length-1;
e>=0;e--){this.containers[e]._trigger("deactivate",null,this._uiHash(this));
if(this.containers[e].containerCache.over){this.containers[e]._trigger("out",null,this._uiHash(this));
this.containers[e].containerCache.over=0
}}}if(this.placeholder){if(this.placeholder[0].parentNode){this.placeholder[0].parentNode.removeChild(this.placeholder[0])
}if(this.options.helper!=="original"&&this.helper&&this.helper[0].parentNode){this.helper.remove()
}b.extend(this,{helper:null,dragging:false,reverting:false,_noFinalSort:null});
if(this.domPosition.prev){b(this.domPosition.prev).after(this.currentItem)
}else{b(this.domPosition.parent).prepend(this.currentItem)
}}return this
},serialize:function(g){var e=this._getItemsAsjQuery(g&&g.connected),f=[];
g=g||{};b(e).each(function(){var h=(b(g.item||this).attr(g.attribute||"id")||"").match(g.expression||(/(.+)[\-=_](.+)/));
if(h){f.push((g.key||h[1]+"[]")+"="+(g.key&&g.expression?h[1]:h[2]))
}});if(!f.length&&g.key){f.push(g.key+"=")
}return f.join("&")
},toArray:function(g){var e=this._getItemsAsjQuery(g&&g.connected),f=[];
g=g||{};e.each(function(){f.push(b(g.item||this).attr(g.attribute||"id")||"")
});return f
},_intersectsWith:function(q){var g=this.positionAbs.left,f=g+this.helperProportions.width,o=this.positionAbs.top,n=o+this.helperProportions.height,h=q.left,e=h+q.width,s=q.top,m=s+q.height,u=this.offset.click.top,k=this.offset.click.left,j=(this.options.axis==="x")||((o+u)>s&&(o+u)<m),p=(this.options.axis==="y")||((g+k)>h&&(g+k)<e),i=j&&p;
if(this.options.tolerance==="pointer"||this.options.forcePointerForContainers||(this.options.tolerance!=="pointer"&&this.helperProportions[this.floating?"width":"height"]>q[this.floating?"width":"height"])){return i
}else{return(h<g+(this.helperProportions.width/2)&&f-(this.helperProportions.width/2)<e&&s<o+(this.helperProportions.height/2)&&n-(this.helperProportions.height/2)<m)
}},_intersectsWithPointer:function(g){var h=(this.options.axis==="x")||a(this.positionAbs.top+this.offset.click.top,g.top,g.height),f=(this.options.axis==="y")||a(this.positionAbs.left+this.offset.click.left,g.left,g.width),j=h&&f,e=this._getDragVerticalDirection(),i=this._getDragHorizontalDirection();
if(!j){return false
}return this.floating?(((i&&i==="right")||e==="down")?2:1):(e&&(e==="down"?2:1))
},_intersectsWithSides:function(h){var f=a(this.positionAbs.top+this.offset.click.top,h.top+(h.height/2),h.height),g=a(this.positionAbs.left+this.offset.click.left,h.left+(h.width/2),h.width),e=this._getDragVerticalDirection(),i=this._getDragHorizontalDirection();
if(this.floating&&i){return((i==="right"&&g)||(i==="left"&&!g))
}else{return e&&((e==="down"&&f)||(e==="up"&&!f))
}},_getDragVerticalDirection:function(){var e=this.positionAbs.top-this.lastPositionAbs.top;
return e!==0&&(e>0?"down":"up")
},_getDragHorizontalDirection:function(){var e=this.positionAbs.left-this.lastPositionAbs.left;
return e!==0&&(e>0?"right":"left")
},refresh:function(e){this._refreshItems(e);
this.refreshPositions();
return this
},_connectWith:function(){var e=this.options;
return e.connectWith.constructor===String?[e.connectWith]:e.connectWith
},_getItemsAsjQuery:function(l){var h,g,n,m,e=[],f=[],k=this._connectWith();
if(k&&l){for(h=k.length-1;
h>=0;h--){n=b(k[h]);
for(g=n.length-1;
g>=0;g--){m=b.data(n[g],this.widgetFullName);
if(m&&m!==this&&!m.options.disabled){f.push([b.isFunction(m.options.items)?m.options.items.call(m.element):b(m.options.items,m.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"),m])
}}}}f.push([b.isFunction(this.options.items)?this.options.items.call(this.element,null,{options:this.options,item:this.currentItem}):b(this.options.items,this.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"),this]);
for(h=f.length-1;
h>=0;h--){f[h][0].each(function(){e.push(this)
})}return b(e)
},_removeCurrentsFromItems:function(){var e=this.currentItem.find(":data("+this.widgetName+"-item)");
this.items=b.grep(this.items,function(g){for(var f=0;
f<e.length;
f++){if(e[f]===g.item[0]){return false
}}return true
})},_refreshItems:function(e){this.items=[];
this.containers=[this];
var k,g,p,l,o,f,r,q,m=this.items,h=[[b.isFunction(this.options.items)?this.options.items.call(this.element[0],e,{item:this.currentItem}):b(this.options.items,this.element),this]],n=this._connectWith();
if(n&&this.ready){for(k=n.length-1;
k>=0;k--){p=b(n[k]);
for(g=p.length-1;
g>=0;g--){l=b.data(p[g],this.widgetFullName);
if(l&&l!==this&&!l.options.disabled){h.push([b.isFunction(l.options.items)?l.options.items.call(l.element[0],e,{item:this.currentItem}):b(l.options.items,l.element),l]);
this.containers.push(l)
}}}}for(k=h.length-1;
k>=0;k--){o=h[k][1];
f=h[k][0];for(g=0,q=f.length;
g<q;g++){r=b(f[g]);
r.data(this.widgetName+"-item",o);
m.push({item:r,instance:o,width:0,height:0,left:0,top:0})
}}},refreshPositions:function(e){if(this.offsetParent&&this.helper){this.offset.parent=this._getParentOffset()
}var g,h,f,j;
for(g=this.items.length-1;
g>=0;g--){h=this.items[g];
if(h.instance!==this.currentContainer&&this.currentContainer&&h.item[0]!==this.currentItem[0]){continue
}f=this.options.toleranceElement?b(this.options.toleranceElement,h.item):h.item;
if(!e){h.width=f.outerWidth();
h.height=f.outerHeight()
}j=f.offset();
h.left=j.left;
h.top=j.top
}if(this.options.custom&&this.options.custom.refreshContainers){this.options.custom.refreshContainers.call(this)
}else{for(g=this.containers.length-1;
g>=0;g--){j=this.containers[g].element.offset();
this.containers[g].containerCache.left=j.left;
this.containers[g].containerCache.top=j.top;
this.containers[g].containerCache.width=this.containers[g].element.outerWidth();
this.containers[g].containerCache.height=this.containers[g].element.outerHeight()
}}return this
},_createPlaceholder:function(f){f=f||this;
var e,g=f.options;
if(!g.placeholder||g.placeholder.constructor===String){e=g.placeholder;
g.placeholder={element:function(){var i=f.currentItem[0].nodeName.toLowerCase(),h=b("<"+i+">",f.document[0]).addClass(e||f.currentItem[0].className+" ui-sortable-placeholder").removeClass("ui-sortable-helper");
if(i==="tr"){f.currentItem.children().each(function(){b("<td>&#160;</td>",f.document[0]).attr("colspan",b(this).attr("colspan")||1).appendTo(h)
})}else{if(i==="img"){h.attr("src",f.currentItem.attr("src"))
}}if(!e){h.css("visibility","hidden")
}return h},update:function(h,i){if(e&&!g.forcePlaceholderSize){return
}if(!i.height()){i.height(f.currentItem.innerHeight()-parseInt(f.currentItem.css("paddingTop")||0,10)-parseInt(f.currentItem.css("paddingBottom")||0,10))
}if(!i.width()){i.width(f.currentItem.innerWidth()-parseInt(f.currentItem.css("paddingLeft")||0,10)-parseInt(f.currentItem.css("paddingRight")||0,10))
}}}}f.placeholder=b(g.placeholder.element.call(f.element,f.currentItem));
f.currentItem.after(f.placeholder);
g.placeholder.update(f,f.placeholder)
},_contactContainers:function(e){var l,h,p,m,n,r,f,s,k,o,g=null,q=null;
for(l=this.containers.length-1;
l>=0;l--){if(b.contains(this.currentItem[0],this.containers[l].element[0])){continue
}if(this._intersectsWith(this.containers[l].containerCache)){if(g&&b.contains(this.containers[l].element[0],g.element[0])){continue
}g=this.containers[l];
q=l}else{if(this.containers[l].containerCache.over){this.containers[l]._trigger("out",e,this._uiHash(this));
this.containers[l].containerCache.over=0
}}}if(!g){return
}if(this.containers.length===1){if(!this.containers[q].containerCache.over){this.containers[q]._trigger("over",e,this._uiHash(this));
this.containers[q].containerCache.over=1
}}else{p=10000;
m=null;o=g.floating||c(this.currentItem);
n=o?"left":"top";
r=o?"width":"height";
f=this.positionAbs[n]+this.offset.click[n];
for(h=this.items.length-1;
h>=0;h--){if(!b.contains(this.containers[q].element[0],this.items[h].item[0])){continue
}if(this.items[h].item[0]===this.currentItem[0]){continue
}if(o&&!a(this.positionAbs.top+this.offset.click.top,this.items[h].top,this.items[h].height)){continue
}s=this.items[h].item.offset()[n];
k=false;if(Math.abs(s-f)>Math.abs(s+this.items[h][r]-f)){k=true;
s+=this.items[h][r]
}if(Math.abs(s-f)<p){p=Math.abs(s-f);
m=this.items[h];
this.direction=k?"up":"down"
}}if(!m&&!this.options.dropOnEmpty){return
}if(this.currentContainer===this.containers[q]){return
}m?this._rearrange(e,m,null,true):this._rearrange(e,null,this.containers[q].element,true);
this._trigger("change",e,this._uiHash());
this.containers[q]._trigger("change",e,this._uiHash(this));
this.currentContainer=this.containers[q];
this.options.placeholder.update(this.currentContainer,this.placeholder);
this.containers[q]._trigger("over",e,this._uiHash(this));
this.containers[q].containerCache.over=1
}},_createHelper:function(f){var g=this.options,e=b.isFunction(g.helper)?b(g.helper.apply(this.element[0],[f,this.currentItem])):(g.helper==="clone"?this.currentItem.clone():this.currentItem);
if(!e.parents("body").length){b(g.appendTo!=="parent"?g.appendTo:this.currentItem[0].parentNode)[0].appendChild(e[0])
}if(e[0]===this.currentItem[0]){this._storedCSS={width:this.currentItem[0].style.width,height:this.currentItem[0].style.height,position:this.currentItem.css("position"),top:this.currentItem.css("top"),left:this.currentItem.css("left")}
}if(!e[0].style.width||g.forceHelperSize){e.width(this.currentItem.width())
}if(!e[0].style.height||g.forceHelperSize){e.height(this.currentItem.height())
}return e},_adjustOffsetFromHelper:function(e){if(typeof e==="string"){e=e.split(" ")
}if(b.isArray(e)){e={left:+e[0],top:+e[1]||0}
}if("left" in e){this.offset.click.left=e.left+this.margins.left
}if("right" in e){this.offset.click.left=this.helperProportions.width-e.right+this.margins.left
}if("top" in e){this.offset.click.top=e.top+this.margins.top
}if("bottom" in e){this.offset.click.top=this.helperProportions.height-e.bottom+this.margins.top
}},_getParentOffset:function(){this.offsetParent=this.helper.offsetParent();
var e=this.offsetParent.offset();
if(this.cssPosition==="absolute"&&this.scrollParent[0]!==document&&b.contains(this.scrollParent[0],this.offsetParent[0])){e.left+=this.scrollParent.scrollLeft();
e.top+=this.scrollParent.scrollTop()
}if(this.offsetParent[0]===document.body||(this.offsetParent[0].tagName&&this.offsetParent[0].tagName.toLowerCase()==="html"&&b.ui.ie)){e={top:0,left:0}
}return{top:e.top+(parseInt(this.offsetParent.css("borderTopWidth"),10)||0),left:e.left+(parseInt(this.offsetParent.css("borderLeftWidth"),10)||0)}
},_getRelativeOffset:function(){if(this.cssPosition==="relative"){var e=this.currentItem.position();
return{top:e.top-(parseInt(this.helper.css("top"),10)||0)+this.scrollParent.scrollTop(),left:e.left-(parseInt(this.helper.css("left"),10)||0)+this.scrollParent.scrollLeft()}
}else{return{top:0,left:0}
}},_cacheMargins:function(){this.margins={left:(parseInt(this.currentItem.css("marginLeft"),10)||0),top:(parseInt(this.currentItem.css("marginTop"),10)||0)}
},_cacheHelperProportions:function(){this.helperProportions={width:this.helper.outerWidth(),height:this.helper.outerHeight()}
},_setContainment:function(){var f,h,e,g=this.options;
if(g.containment==="parent"){g.containment=this.helper[0].parentNode
}if(g.containment==="document"||g.containment==="window"){this.containment=[0-this.offset.relative.left-this.offset.parent.left,0-this.offset.relative.top-this.offset.parent.top,b(g.containment==="document"?document:window).width()-this.helperProportions.width-this.margins.left,(b(g.containment==="document"?document:window).height()||document.body.parentNode.scrollHeight)-this.helperProportions.height-this.margins.top]
}if(!(/^(document|window|parent)$/).test(g.containment)){f=b(g.containment)[0];
h=b(g.containment).offset();
e=(b(f).css("overflow")!=="hidden");
this.containment=[h.left+(parseInt(b(f).css("borderLeftWidth"),10)||0)+(parseInt(b(f).css("paddingLeft"),10)||0)-this.margins.left,h.top+(parseInt(b(f).css("borderTopWidth"),10)||0)+(parseInt(b(f).css("paddingTop"),10)||0)-this.margins.top,h.left+(e?Math.max(f.scrollWidth,f.offsetWidth):f.offsetWidth)-(parseInt(b(f).css("borderLeftWidth"),10)||0)-(parseInt(b(f).css("paddingRight"),10)||0)-this.helperProportions.width-this.margins.left,h.top+(e?Math.max(f.scrollHeight,f.offsetHeight):f.offsetHeight)-(parseInt(b(f).css("borderTopWidth"),10)||0)-(parseInt(b(f).css("paddingBottom"),10)||0)-this.helperProportions.height-this.margins.top]
}},_convertPositionTo:function(g,i){if(!i){i=this.position
}var f=g==="absolute"?1:-1,e=this.cssPosition==="absolute"&&!(this.scrollParent[0]!==document&&b.contains(this.scrollParent[0],this.offsetParent[0]))?this.offsetParent:this.scrollParent,h=(/(html|body)/i).test(e[0].tagName);
return{top:(i.top+this.offset.relative.top*f+this.offset.parent.top*f-((this.cssPosition==="fixed"?-this.scrollParent.scrollTop():(h?0:e.scrollTop()))*f)),left:(i.left+this.offset.relative.left*f+this.offset.parent.left*f-((this.cssPosition==="fixed"?-this.scrollParent.scrollLeft():h?0:e.scrollLeft())*f))}
},_generatePosition:function(h){var j,i,k=this.options,g=h.pageX,f=h.pageY,e=this.cssPosition==="absolute"&&!(this.scrollParent[0]!==document&&b.contains(this.scrollParent[0],this.offsetParent[0]))?this.offsetParent:this.scrollParent,l=(/(html|body)/i).test(e[0].tagName);
if(this.cssPosition==="relative"&&!(this.scrollParent[0]!==document&&this.scrollParent[0]!==this.offsetParent[0])){this.offset.relative=this._getRelativeOffset()
}if(this.originalPosition){if(this.containment){if(h.pageX-this.offset.click.left<this.containment[0]){g=this.containment[0]+this.offset.click.left
}if(h.pageY-this.offset.click.top<this.containment[1]){f=this.containment[1]+this.offset.click.top
}if(h.pageX-this.offset.click.left>this.containment[2]){g=this.containment[2]+this.offset.click.left
}if(h.pageY-this.offset.click.top>this.containment[3]){f=this.containment[3]+this.offset.click.top
}}if(k.grid){j=this.originalPageY+Math.round((f-this.originalPageY)/k.grid[1])*k.grid[1];
f=this.containment?((j-this.offset.click.top>=this.containment[1]&&j-this.offset.click.top<=this.containment[3])?j:((j-this.offset.click.top>=this.containment[1])?j-k.grid[1]:j+k.grid[1])):j;
i=this.originalPageX+Math.round((g-this.originalPageX)/k.grid[0])*k.grid[0];
g=this.containment?((i-this.offset.click.left>=this.containment[0]&&i-this.offset.click.left<=this.containment[2])?i:((i-this.offset.click.left>=this.containment[0])?i-k.grid[0]:i+k.grid[0])):i
}}return{top:(f-this.offset.click.top-this.offset.relative.top-this.offset.parent.top+((this.cssPosition==="fixed"?-this.scrollParent.scrollTop():(l?0:e.scrollTop())))),left:(g-this.offset.click.left-this.offset.relative.left-this.offset.parent.left+((this.cssPosition==="fixed"?-this.scrollParent.scrollLeft():l?0:e.scrollLeft())))}
},_rearrange:function(j,h,f,g){f?f[0].appendChild(this.placeholder[0]):h.item[0].parentNode.insertBefore(this.placeholder[0],(this.direction==="down"?h.item[0]:h.item[0].nextSibling));
this.counter=this.counter?++this.counter:1;
var e=this.counter;
this._delay(function(){if(e===this.counter){this.refreshPositions(!g)
}})},_clear:function(f,g){this.reverting=false;
var e,h=[];
if(!this._noFinalSort&&this.currentItem.parent().length){this.placeholder.before(this.currentItem)
}this._noFinalSort=null;
if(this.helper[0]===this.currentItem[0]){for(e in this._storedCSS){if(this._storedCSS[e]==="auto"||this._storedCSS[e]==="static"){this._storedCSS[e]=""
}}this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper")
}else{this.currentItem.show()
}if(this.fromOutside&&!g){h.push(function(i){this._trigger("receive",i,this._uiHash(this.fromOutside))
})}if((this.fromOutside||this.domPosition.prev!==this.currentItem.prev().not(".ui-sortable-helper")[0]||this.domPosition.parent!==this.currentItem.parent()[0])&&!g){h.push(function(i){this._trigger("update",i,this._uiHash())
})}if(this!==this.currentContainer){if(!g){h.push(function(i){this._trigger("remove",i,this._uiHash())
});h.push((function(i){return function(j){i._trigger("receive",j,this._uiHash(this))
}}).call(this,this.currentContainer));
h.push((function(i){return function(j){i._trigger("update",j,this._uiHash(this))
}}).call(this,this.currentContainer))
}}for(e=this.containers.length-1;
e>=0;e--){if(!g){h.push((function(i){return function(j){i._trigger("deactivate",j,this._uiHash(this))
}}).call(this,this.containers[e]))
}if(this.containers[e].containerCache.over){h.push((function(i){return function(j){i._trigger("out",j,this._uiHash(this))
}}).call(this,this.containers[e]));
this.containers[e].containerCache.over=0
}}if(this.storedCursor){this.document.find("body").css("cursor",this.storedCursor);
this.storedStylesheet.remove()
}if(this._storedOpacity){this.helper.css("opacity",this._storedOpacity)
}if(this._storedZIndex){this.helper.css("zIndex",this._storedZIndex==="auto"?"":this._storedZIndex)
}this.dragging=false;
if(this.cancelHelperRemoval){if(!g){this._trigger("beforeStop",f,this._uiHash());
for(e=0;e<h.length;
e++){h[e].call(this,f)
}this._trigger("stop",f,this._uiHash())
}this.fromOutside=false;
return false
}if(!g){this._trigger("beforeStop",f,this._uiHash())
}this.placeholder[0].parentNode.removeChild(this.placeholder[0]);
if(this.helper[0]!==this.currentItem[0]){this.helper.remove()
}this.helper=null;
if(!g){for(e=0;
e<h.length;
e++){h[e].call(this,f)
}this._trigger("stop",f,this._uiHash())
}this.fromOutside=false;
return true
},_trigger:function(){if(b.Widget.prototype._trigger.apply(this,arguments)===false){this.cancel()
}},_uiHash:function(e){var f=e||this;
return{helper:f.helper,placeholder:f.placeholder||b([]),position:f.position,originalPosition:f.originalPosition,offset:f.positionAbs,item:f.currentItem,sender:e?e.element:null}
}})})(jQuery);
(function(d,e){var b=0,c={},a={};
c.height=c.paddingTop=c.paddingBottom=c.borderTopWidth=c.borderBottomWidth="hide";
a.height=a.paddingTop=a.paddingBottom=a.borderTopWidth=a.borderBottomWidth="show";
d.widget("ui.accordion",{version:"1.10.3",options:{active:0,animate:{},collapsible:false,event:"click",header:"> li > :first-child,> :not(li):even",heightStyle:"auto",icons:{activeHeader:"ui-icon-triangle-1-s",header:"ui-icon-triangle-1-e"},activate:null,beforeActivate:null},_create:function(){var f=this.options;
this.prevShow=this.prevHide=d();
this.element.addClass("ui-accordion ui-widget ui-helper-reset").attr("role","tablist");
if(!f.collapsible&&(f.active===false||f.active==null)){f.active=0
}this._processPanels();
if(f.active<0){f.active+=this.headers.length
}this._refresh()
},_getCreateEventData:function(){return{header:this.active,panel:!this.active.length?d():this.active.next(),content:!this.active.length?d():this.active.next()}
},_createIcons:function(){var f=this.options.icons;
if(f){d("<span>").addClass("ui-accordion-header-icon ui-icon "+f.header).prependTo(this.headers);
this.active.children(".ui-accordion-header-icon").removeClass(f.header).addClass(f.activeHeader);
this.headers.addClass("ui-accordion-icons")
}},_destroyIcons:function(){this.headers.removeClass("ui-accordion-icons").children(".ui-accordion-header-icon").remove()
},_destroy:function(){var f;
this.element.removeClass("ui-accordion ui-widget ui-helper-reset").removeAttr("role");
this.headers.removeClass("ui-accordion-header ui-accordion-header-active ui-helper-reset ui-state-default ui-corner-all ui-state-active ui-state-disabled ui-corner-top").removeAttr("role").removeAttr("aria-selected").removeAttr("aria-controls").removeAttr("tabIndex").each(function(){if(/^ui-accordion/.test(this.id)){this.removeAttribute("id")
}});this._destroyIcons();
f=this.headers.next().css("display","").removeAttr("role").removeAttr("aria-expanded").removeAttr("aria-hidden").removeAttr("aria-labelledby").removeClass("ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content ui-accordion-content-active ui-state-disabled").each(function(){if(/^ui-accordion/.test(this.id)){this.removeAttribute("id")
}});if(this.options.heightStyle!=="content"){f.css("height","")
}},_setOption:function(f,g){if(f==="active"){this._activate(g);
return}if(f==="event"){if(this.options.event){this._off(this.headers,this.options.event)
}this._setupEvents(g)
}this._super(f,g);
if(f==="collapsible"&&!g&&this.options.active===false){this._activate(0)
}if(f==="icons"){this._destroyIcons();
if(g){this._createIcons()
}}if(f==="disabled"){this.headers.add(this.headers.next()).toggleClass("ui-state-disabled",!!g)
}},_keydown:function(i){if(i.altKey||i.ctrlKey){return
}var j=d.ui.keyCode,h=this.headers.length,f=this.headers.index(i.target),g=false;
switch(i.keyCode){case j.RIGHT:case j.DOWN:g=this.headers[(f+1)%h];
break;case j.LEFT:case j.UP:g=this.headers[(f-1+h)%h];
break;case j.SPACE:case j.ENTER:this._eventHandler(i);
break;case j.HOME:g=this.headers[0];
break;case j.END:g=this.headers[h-1];
break}if(g){d(i.target).attr("tabIndex",-1);
d(g).attr("tabIndex",0);
g.focus();i.preventDefault()
}},_panelKeyDown:function(f){if(f.keyCode===d.ui.keyCode.UP&&f.ctrlKey){d(f.currentTarget).prev().focus()
}},refresh:function(){var f=this.options;
this._processPanels();
if((f.active===false&&f.collapsible===true)||!this.headers.length){f.active=false;
this.active=d()
}else{if(f.active===false){this._activate(0)
}else{if(this.active.length&&!d.contains(this.element[0],this.active[0])){if(this.headers.length===this.headers.find(".ui-state-disabled").length){f.active=false;
this.active=d()
}else{this._activate(Math.max(0,f.active-1))
}}else{f.active=this.headers.index(this.active)
}}}this._destroyIcons();
this._refresh()
},_processPanels:function(){this.headers=this.element.find(this.options.header).addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-all");
this.headers.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").filter(":not(.ui-accordion-content-active)").hide()
},_refresh:function(){var j,h=this.options,g=h.heightStyle,i=this.element.parent(),f=this.accordionId="ui-accordion-"+(this.element.attr("id")||++b);
this.active=this._findActive(h.active).addClass("ui-accordion-header-active ui-state-active ui-corner-top").removeClass("ui-corner-all");
this.active.next().addClass("ui-accordion-content-active").show();
this.headers.attr("role","tab").each(function(n){var o=d(this),m=o.attr("id"),k=o.next(),l=k.attr("id");
if(!m){m=f+"-header-"+n;
o.attr("id",m)
}if(!l){l=f+"-panel-"+n;
k.attr("id",l)
}o.attr("aria-controls",l);
k.attr("aria-labelledby",m)
}).next().attr("role","tabpanel");
this.headers.not(this.active).attr({"aria-selected":"false",tabIndex:-1}).next().attr({"aria-expanded":"false","aria-hidden":"true"}).hide();
if(!this.active.length){this.headers.eq(0).attr("tabIndex",0)
}else{this.active.attr({"aria-selected":"true",tabIndex:0}).next().attr({"aria-expanded":"true","aria-hidden":"false"})
}this._createIcons();
this._setupEvents(h.event);
if(g==="fill"){j=i.height();
this.element.siblings(":visible").each(function(){var l=d(this),k=l.css("position");
if(k==="absolute"||k==="fixed"){return
}j-=l.outerHeight(true)
});this.headers.each(function(){j-=d(this).outerHeight(true)
});this.headers.next().each(function(){d(this).height(Math.max(0,j-d(this).innerHeight()+d(this).height()))
}).css("overflow","auto")
}else{if(g==="auto"){j=0;
this.headers.next().each(function(){j=Math.max(j,d(this).css("height","").height())
}).height(j)
}}},_activate:function(f){var g=this._findActive(f)[0];
if(g===this.active[0]){return
}g=g||this.active[0];
this._eventHandler({target:g,currentTarget:g,preventDefault:d.noop})
},_findActive:function(f){return typeof f==="number"?this.headers.eq(f):d()
},_setupEvents:function(g){var f={keydown:"_keydown"};
if(g){d.each(g.split(" "),function(i,h){f[h]="_eventHandler"
})}this._off(this.headers.add(this.headers.next()));
this._on(this.headers,f);
this._on(this.headers.next(),{keydown:"_panelKeyDown"});
this._hoverable(this.headers);
this._focusable(this.headers)
},_eventHandler:function(f){var n=this.options,i=this.active,j=d(f.currentTarget),l=j[0]===i[0],g=l&&n.collapsible,h=g?d():j.next(),k=i.next(),m={oldHeader:i,oldPanel:k,newHeader:g?d():j,newPanel:h};
f.preventDefault();
if((l&&!n.collapsible)||(this._trigger("beforeActivate",f,m)===false)){return
}n.active=g?false:this.headers.index(j);
this.active=l?d():j;
this._toggle(m);
i.removeClass("ui-accordion-header-active ui-state-active");
if(n.icons){i.children(".ui-accordion-header-icon").removeClass(n.icons.activeHeader).addClass(n.icons.header)
}if(!l){j.removeClass("ui-corner-all").addClass("ui-accordion-header-active ui-state-active ui-corner-top");
if(n.icons){j.children(".ui-accordion-header-icon").removeClass(n.icons.header).addClass(n.icons.activeHeader)
}j.next().addClass("ui-accordion-content-active")
}},_toggle:function(h){var f=h.newPanel,g=this.prevShow.length?this.prevShow:h.oldPanel;
this.prevShow.add(this.prevHide).stop(true,true);
this.prevShow=f;
this.prevHide=g;
if(this.options.animate){this._animate(f,g,h)
}else{g.hide();
f.show();this._toggleComplete(h)
}g.attr({"aria-expanded":"false","aria-hidden":"true"});
g.prev().attr("aria-selected","false");
if(f.length&&g.length){g.prev().attr("tabIndex",-1)
}else{if(f.length){this.headers.filter(function(){return d(this).attr("tabIndex")===0
}).attr("tabIndex",-1)
}}f.attr({"aria-expanded":"true","aria-hidden":"false"}).prev().attr({"aria-selected":"true",tabIndex:0})
},_animate:function(f,n,j){var m,l,i,k=this,o=0,p=f.length&&(!n.length||(f.index()<n.index())),h=this.options.animate||{},q=p&&h.down||h,g=function(){k._toggleComplete(j)
};if(typeof q==="number"){i=q
}if(typeof q==="string"){l=q
}l=l||q.easing||h.easing;
i=i||q.duration||h.duration;
if(!n.length){return f.animate(a,i,l,g)
}if(!f.length){return n.animate(c,i,l,g)
}m=f.show().outerHeight();
n.animate(c,{duration:i,easing:l,step:function(r,s){s.now=Math.round(r)
}});f.hide().animate(a,{duration:i,easing:l,complete:g,step:function(r,s){s.now=Math.round(r);
if(s.prop!=="height"){o+=s.now
}else{if(k.options.heightStyle!=="content"){s.now=Math.round(m-n.outerHeight()-o);
o=0}}}})},_toggleComplete:function(g){var f=g.oldPanel;
f.removeClass("ui-accordion-content-active").prev().removeClass("ui-corner-top").addClass("ui-corner-all");
if(f.length){f.parent()[0].className=f.parent()[0].className
}this._trigger("activate",null,g)
}})})(jQuery);
(function(a,b){var c=0;
a.widget("ui.autocomplete",{version:"1.10.3",defaultElement:"<input>",options:{appendTo:null,autoFocus:false,delay:300,minLength:1,position:{my:"left top",at:"left bottom",collision:"none"},source:null,change:null,close:null,focus:null,open:null,response:null,search:null,select:null},pending:0,_create:function(){var f,d,g,i=this.element[0].nodeName.toLowerCase(),h=i==="textarea",e=i==="input";
this.isMultiLine=h?true:e?false:this.element.prop("isContentEditable");
this.valueMethod=this.element[h||e?"val":"text"];
this.isNewMenu=true;
this.element.addClass("ui-autocomplete-input").attr("autocomplete","off");
this._on(this.element,{keydown:function(j){if(this.element.prop("readOnly")){f=true;
g=true;d=true;
return}f=false;
g=false;d=false;
var k=a.ui.keyCode;
switch(j.keyCode){case k.PAGE_UP:f=true;
this._move("previousPage",j);
break;case k.PAGE_DOWN:f=true;
this._move("nextPage",j);
break;case k.UP:f=true;
this._keyEvent("previous",j);
break;case k.DOWN:f=true;
this._keyEvent("next",j);
break;case k.ENTER:case k.NUMPAD_ENTER:if(this.menu.active){f=true;
j.preventDefault();
this.menu.select(j)
}break;case k.TAB:if(this.menu.active){this.menu.select(j)
}break;case k.ESCAPE:if(this.menu.element.is(":visible")){this._value(this.term);
this.close(j);
j.preventDefault()
}break;default:d=true;
this._searchTimeout(j);
break}},keypress:function(j){if(f){f=false;
if(!this.isMultiLine||this.menu.element.is(":visible")){j.preventDefault()
}return}if(d){return
}var k=a.ui.keyCode;
switch(j.keyCode){case k.PAGE_UP:this._move("previousPage",j);
break;case k.PAGE_DOWN:this._move("nextPage",j);
break;case k.UP:this._keyEvent("previous",j);
break;case k.DOWN:this._keyEvent("next",j);
break}},input:function(j){if(g){g=false;
j.preventDefault();
return}this._searchTimeout(j)
},focus:function(){this.selectedItem=null;
this.previous=this._value()
},blur:function(j){if(this.cancelBlur){delete this.cancelBlur;
return}clearTimeout(this.searching);
this.close(j);
this._change(j)
}});this._initSource();
this.menu=a("<ul>").addClass("ui-autocomplete ui-front").appendTo(this._appendTo()).menu({role:null}).hide().data("ui-menu");
this._on(this.menu.element,{mousedown:function(j){j.preventDefault();
this.cancelBlur=true;
this._delay(function(){delete this.cancelBlur
});var k=this.menu.element[0];
if(!a(j.target).closest(".ui-menu-item").length){this._delay(function(){var l=this;
this.document.one("mousedown",function(m){if(m.target!==l.element[0]&&m.target!==k&&!a.contains(k,m.target)){l.close()
}})})}},menufocus:function(k,l){if(this.isNewMenu){this.isNewMenu=false;
if(k.originalEvent&&/^mouse/.test(k.originalEvent.type)){this.menu.blur();
this.document.one("mousemove",function(){a(k.target).trigger(k.originalEvent)
});return}}var j=l.item.data("ui-autocomplete-item");
if(false!==this._trigger("focus",k,{item:j})){if(k.originalEvent&&/^key/.test(k.originalEvent.type)){this._value(j.value)
}}else{this.liveRegion.text(j.value)
}},menuselect:function(l,m){var k=m.item.data("ui-autocomplete-item"),j=this.previous;
if(this.element[0]!==this.document[0].activeElement){this.element.focus();
this.previous=j;
this._delay(function(){this.previous=j;
this.selectedItem=k
})}if(false!==this._trigger("select",l,{item:k})){this._value(k.value)
}this.term=this._value();
this.close(l);
this.selectedItem=k
}});this.liveRegion=a("<span>",{role:"status","aria-live":"polite"}).addClass("ui-helper-hidden-accessible").insertBefore(this.element);
this._on(this.window,{beforeunload:function(){this.element.removeAttr("autocomplete")
}})},_destroy:function(){clearTimeout(this.searching);
this.element.removeClass("ui-autocomplete-input").removeAttr("autocomplete");
this.menu.element.remove();
this.liveRegion.remove()
},_setOption:function(d,e){this._super(d,e);
if(d==="source"){this._initSource()
}if(d==="appendTo"){this.menu.element.appendTo(this._appendTo())
}if(d==="disabled"&&e&&this.xhr){this.xhr.abort()
}},_appendTo:function(){var d=this.options.appendTo;
if(d){d=d.jquery||d.nodeType?a(d):this.document.find(d).eq(0)
}if(!d){d=this.element.closest(".ui-front")
}if(!d.length){d=this.document[0].body
}return d},_initSource:function(){var f,d,e=this;
if(a.isArray(this.options.source)){f=this.options.source;
this.source=function(h,g){g(a.ui.autocomplete.filter(f,h.term))
}}else{if(typeof this.options.source==="string"){d=this.options.source;
this.source=function(h,g){if(e.xhr){e.xhr.abort()
}e.xhr=a.ajax({url:d,data:h,dataType:"json",success:function(i){g(i)
},error:function(){g([])
}})}}else{this.source=this.options.source
}}},_searchTimeout:function(d){clearTimeout(this.searching);
this.searching=this._delay(function(){if(this.term!==this._value()){this.selectedItem=null;
this.search(null,d)
}},this.options.delay)
},search:function(e,d){e=e!=null?e:this._value();
this.term=this._value();
if(e.length<this.options.minLength){return this.close(d)
}if(this._trigger("search",d)===false){return
}return this._search(e)
},_search:function(d){this.pending++;
this.element.addClass("ui-autocomplete-loading");
this.cancelSearch=false;
this.source({term:d},this._response())
},_response:function(){var e=this,d=++c;
return function(f){if(d===c){e.__response(f)
}e.pending--;
if(!e.pending){e.element.removeClass("ui-autocomplete-loading")
}}},__response:function(d){if(d){d=this._normalize(d)
}this._trigger("response",null,{content:d});
if(!this.options.disabled&&d&&d.length&&!this.cancelSearch){this._suggest(d);
this._trigger("open")
}else{this._close()
}},close:function(d){this.cancelSearch=true;
this._close(d)
},_close:function(d){if(this.menu.element.is(":visible")){this.menu.element.hide();
this.menu.blur();
this.isNewMenu=true;
this._trigger("close",d)
}},_change:function(d){if(this.previous!==this._value()){this._trigger("change",d,{item:this.selectedItem})
}},_normalize:function(d){if(d.length&&d[0].label&&d[0].value){return d
}return a.map(d,function(e){if(typeof e==="string"){return{label:e,value:e}
}return a.extend({label:e.label||e.value,value:e.value||e.label},e)
})},_suggest:function(d){var e=this.menu.element.empty();
this._renderMenu(e,d);
this.isNewMenu=true;
this.menu.refresh();
e.show();this._resizeMenu();
e.position(a.extend({of:this.element},this.options.position));
if(this.options.autoFocus){this.menu.next()
}},_resizeMenu:function(){var d=this.menu.element;
d.outerWidth(Math.max(d.width("").outerWidth()+1,this.element.outerWidth()))
},_renderMenu:function(e,d){var f=this;
a.each(d,function(g,h){f._renderItemData(e,h)
})},_renderItemData:function(d,e){return this._renderItem(d,e).data("ui-autocomplete-item",e)
},_renderItem:function(d,e){return a("<li>").append(a("<a>").text(e.label)).appendTo(d)
},_move:function(e,d){if(!this.menu.element.is(":visible")){this.search(null,d);
return}if(this.menu.isFirstItem()&&/^previous/.test(e)||this.menu.isLastItem()&&/^next/.test(e)){this._value(this.term);
this.menu.blur();
return}this.menu[e](d)
},widget:function(){return this.menu.element
},_value:function(){return this.valueMethod.apply(this.element,arguments)
},_keyEvent:function(e,d){if(!this.isMultiLine||this.menu.element.is(":visible")){this._move(e,d);
d.preventDefault()
}}});a.extend(a.ui.autocomplete,{escapeRegex:function(d){return d.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g,"\\$&")
},filter:function(f,d){var e=new RegExp(a.ui.autocomplete.escapeRegex(d),"i");
return a.grep(f,function(g){return e.test(g.label||g.value||g)
})}});a.widget("ui.autocomplete",a.ui.autocomplete,{options:{messages:{noResults:"No search results.",results:function(d){return d+(d>1?" results are":" result is")+" available, use up and down arrow keys to navigate."
}}},__response:function(e){var d;
this._superApply(arguments);
if(this.options.disabled||this.cancelSearch){return
}if(e&&e.length){d=this.options.messages.results(e.length)
}else{d=this.options.messages.noResults
}this.liveRegion.text(d)
}})}(jQuery));
(function(f,b){var k,e,a,h,i="ui-button ui-widget ui-state-default ui-corner-all",c="ui-state-hover ui-state-active ",g="ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only",j=function(){var l=f(this);
setTimeout(function(){l.find(":ui-button").button("refresh")
},1)},d=function(m){var l=m.name,n=m.form,o=f([]);
if(l){l=l.replace(/'/g,"\\'");
if(n){o=f(n).find("[name='"+l+"']")
}else{o=f("[name='"+l+"']",m.ownerDocument).filter(function(){return !this.form
})}}return o
};f.widget("ui.button",{version:"1.10.3",defaultElement:"<button>",options:{disabled:null,text:true,label:null,icons:{primary:null,secondary:null}},_create:function(){this.element.closest("form").unbind("reset"+this.eventNamespace).bind("reset"+this.eventNamespace,j);
if(typeof this.options.disabled!=="boolean"){this.options.disabled=!!this.element.prop("disabled")
}else{this.element.prop("disabled",this.options.disabled)
}this._determineButtonType();
this.hasTitle=!!this.buttonElement.attr("title");
var o=this,m=this.options,p=this.type==="checkbox"||this.type==="radio",n=!p?"ui-state-active":"",l="ui-state-focus";
if(m.label===null){m.label=(this.type==="input"?this.buttonElement.val():this.buttonElement.html())
}this._hoverable(this.buttonElement);
this.buttonElement.addClass(i).attr("role","button").bind("mouseenter"+this.eventNamespace,function(){if(m.disabled){return
}if(this===k){f(this).addClass("ui-state-active")
}}).bind("mouseleave"+this.eventNamespace,function(){if(m.disabled){return
}f(this).removeClass(n)
}).bind("click"+this.eventNamespace,function(q){if(m.disabled){q.preventDefault();
q.stopImmediatePropagation()
}});this.element.bind("focus"+this.eventNamespace,function(){o.buttonElement.addClass(l)
}).bind("blur"+this.eventNamespace,function(){o.buttonElement.removeClass(l)
});if(p){this.element.bind("change"+this.eventNamespace,function(){if(h){return
}o.refresh()
});this.buttonElement.bind("mousedown"+this.eventNamespace,function(q){if(m.disabled){return
}h=false;e=q.pageX;
a=q.pageY}).bind("mouseup"+this.eventNamespace,function(q){if(m.disabled){return
}if(e!==q.pageX||a!==q.pageY){h=true
}})}if(this.type==="checkbox"){this.buttonElement.bind("click"+this.eventNamespace,function(){if(m.disabled||h){return false
}})}else{if(this.type==="radio"){this.buttonElement.bind("click"+this.eventNamespace,function(){if(m.disabled||h){return false
}f(this).addClass("ui-state-active");
o.buttonElement.attr("aria-pressed","true");
var q=o.element[0];
d(q).not(q).map(function(){return f(this).button("widget")[0]
}).removeClass("ui-state-active").attr("aria-pressed","false")
})}else{this.buttonElement.bind("mousedown"+this.eventNamespace,function(){if(m.disabled){return false
}f(this).addClass("ui-state-active");
k=this;o.document.one("mouseup",function(){k=null
})}).bind("mouseup"+this.eventNamespace,function(){if(m.disabled){return false
}f(this).removeClass("ui-state-active")
}).bind("keydown"+this.eventNamespace,function(q){if(m.disabled){return false
}if(q.keyCode===f.ui.keyCode.SPACE||q.keyCode===f.ui.keyCode.ENTER){f(this).addClass("ui-state-active")
}}).bind("keyup"+this.eventNamespace+" blur"+this.eventNamespace,function(){f(this).removeClass("ui-state-active")
});if(this.buttonElement.is("a")){this.buttonElement.keyup(function(q){if(q.keyCode===f.ui.keyCode.SPACE){f(this).click()
}})}}}this._setOption("disabled",m.disabled);
this._resetButton()
},_determineButtonType:function(){var l,n,m;
if(this.element.is("[type=checkbox]")){this.type="checkbox"
}else{if(this.element.is("[type=radio]")){this.type="radio"
}else{if(this.element.is("input")){this.type="input"
}else{this.type="button"
}}}if(this.type==="checkbox"||this.type==="radio"){l=this.element.parents().last();
n="label[for='"+this.element.attr("id")+"']";
this.buttonElement=l.find(n);
if(!this.buttonElement.length){l=l.length?l.siblings():this.element.siblings();
this.buttonElement=l.filter(n);
if(!this.buttonElement.length){this.buttonElement=l.find(n)
}}this.element.addClass("ui-helper-hidden-accessible");
m=this.element.is(":checked");
if(m){this.buttonElement.addClass("ui-state-active")
}this.buttonElement.prop("aria-pressed",m)
}else{this.buttonElement=this.element
}},widget:function(){return this.buttonElement
},_destroy:function(){this.element.removeClass("ui-helper-hidden-accessible");
this.buttonElement.removeClass(i+" "+c+" "+g).removeAttr("role").removeAttr("aria-pressed").html(this.buttonElement.find(".ui-button-text").html());
if(!this.hasTitle){this.buttonElement.removeAttr("title")
}},_setOption:function(l,m){this._super(l,m);
if(l==="disabled"){if(m){this.element.prop("disabled",true)
}else{this.element.prop("disabled",false)
}return}this._resetButton()
},refresh:function(){var l=this.element.is("input, button")?this.element.is(":disabled"):this.element.hasClass("ui-button-disabled");
if(l!==this.options.disabled){this._setOption("disabled",l)
}if(this.type==="radio"){d(this.element[0]).each(function(){if(f(this).is(":checked")){f(this).button("widget").addClass("ui-state-active").attr("aria-pressed","true")
}else{f(this).button("widget").removeClass("ui-state-active").attr("aria-pressed","false")
}})}else{if(this.type==="checkbox"){if(this.element.is(":checked")){this.buttonElement.addClass("ui-state-active").attr("aria-pressed","true")
}else{this.buttonElement.removeClass("ui-state-active").attr("aria-pressed","false")
}}}},_resetButton:function(){if(this.type==="input"){if(this.options.label){this.element.val(this.options.label)
}return}var p=this.buttonElement.removeClass(g),n=f("<span></span>",this.document[0]).addClass("ui-button-text").html(this.options.label).appendTo(p.empty()).text(),m=this.options.icons,l=m.primary&&m.secondary,o=[];
if(m.primary||m.secondary){if(this.options.text){o.push("ui-button-text-icon"+(l?"s":(m.primary?"-primary":"-secondary")))
}if(m.primary){p.prepend("<span class='ui-button-icon-primary ui-icon "+m.primary+"'></span>")
}if(m.secondary){p.append("<span class='ui-button-icon-secondary ui-icon "+m.secondary+"'></span>")
}if(!this.options.text){o.push(l?"ui-button-icons-only":"ui-button-icon-only");
if(!this.hasTitle){p.attr("title",f.trim(n))
}}}else{o.push("ui-button-text-only")
}p.addClass(o.join(" "))
}});f.widget("ui.buttonset",{version:"1.10.3",options:{items:"button, input[type=button], input[type=submit], input[type=reset], input[type=checkbox], input[type=radio], a, :data(ui-button)"},_create:function(){this.element.addClass("ui-buttonset")
},_init:function(){this.refresh()
},_setOption:function(l,m){if(l==="disabled"){this.buttons.button("option",l,m)
}this._super(l,m)
},refresh:function(){var l=this.element.css("direction")==="rtl";
this.buttons=this.element.find(this.options.items).filter(":ui-button").button("refresh").end().not(":ui-button").button().end().map(function(){return f(this).button("widget")[0]
}).removeClass("ui-corner-all ui-corner-left ui-corner-right").filter(":first").addClass(l?"ui-corner-right":"ui-corner-left").end().filter(":last").addClass(l?"ui-corner-left":"ui-corner-right").end().end()
},_destroy:function(){this.element.removeClass("ui-buttonset");
this.buttons.map(function(){return f(this).button("widget")[0]
}).removeClass("ui-corner-left ui-corner-right").end().button("destroy")
}})}(jQuery));
(function(c,d){var a={buttons:true,height:true,maxHeight:true,maxWidth:true,minHeight:true,minWidth:true,width:true},b={maxHeight:true,maxWidth:true,minHeight:true,minWidth:true};
c.widget("ui.dialog",{version:"1.10.3",options:{appendTo:"body",autoOpen:true,buttons:[],closeOnEscape:true,closeText:"close",dialogClass:"",draggable:true,hide:null,height:"auto",maxHeight:null,maxWidth:null,minHeight:150,minWidth:150,modal:false,position:{my:"center",at:"center",of:window,collision:"fit",using:function(f){var e=c(this).css(f).offset().top;
if(e<0){c(this).css("top",f.top-e)
}}},resizable:true,show:null,title:null,width:300,beforeClose:null,close:null,drag:null,dragStart:null,dragStop:null,focus:null,open:null,resize:null,resizeStart:null,resizeStop:null},_create:function(){this.originalCss={display:this.element[0].style.display,width:this.element[0].style.width,minHeight:this.element[0].style.minHeight,maxHeight:this.element[0].style.maxHeight,height:this.element[0].style.height};
this.originalPosition={parent:this.element.parent(),index:this.element.parent().children().index(this.element)};
this.originalTitle=this.element.attr("title");
this.options.title=this.options.title||this.originalTitle;
this._createWrapper();
this.element.show().removeAttr("title").addClass("ui-dialog-content ui-widget-content").appendTo(this.uiDialog);
this._createTitlebar();
this._createButtonPane();
if(this.options.draggable&&c.fn.draggable){this._makeDraggable()
}if(this.options.resizable&&c.fn.resizable){this._makeResizable()
}this._isOpen=false
},_init:function(){if(this.options.autoOpen){this.open()
}},_appendTo:function(){var e=this.options.appendTo;
if(e&&(e.jquery||e.nodeType)){return c(e)
}return this.document.find(e||"body").eq(0)
},_destroy:function(){var f,e=this.originalPosition;
this._destroyOverlay();
this.element.removeUniqueId().removeClass("ui-dialog-content ui-widget-content").css(this.originalCss).detach();
this.uiDialog.stop(true,true).remove();
if(this.originalTitle){this.element.attr("title",this.originalTitle)
}f=e.parent.children().eq(e.index);
if(f.length&&f[0]!==this.element[0]){f.before(this.element)
}else{e.parent.append(this.element)
}},widget:function(){return this.uiDialog
},disable:c.noop,enable:c.noop,close:function(f){var e=this;
if(!this._isOpen||this._trigger("beforeClose",f)===false){return
}this._isOpen=false;
this._destroyOverlay();
if(!this.opener.filter(":focusable").focus().length){c(this.document[0].activeElement).blur()
}this._hide(this.uiDialog,this.options.hide,function(){e._trigger("close",f)
})},isOpen:function(){return this._isOpen
},moveToTop:function(){this._moveToTop()
},_moveToTop:function(g,e){var f=!!this.uiDialog.nextAll(":visible").insertBefore(this.uiDialog).length;
if(f&&!e){this._trigger("focus",g)
}return f},open:function(){var e=this;
if(this._isOpen){if(this._moveToTop()){this._focusTabbable()
}return}this._isOpen=true;
this.opener=c(this.document[0].activeElement);
this._size();
this._position();
this._createOverlay();
this._moveToTop(null,true);
this._show(this.uiDialog,this.options.show,function(){e._focusTabbable();
e._trigger("focus")
});this._trigger("open")
},_focusTabbable:function(){var e=this.element.find("[autofocus]");
if(!e.length){e=this.element.find(":tabbable")
}if(!e.length){e=this.uiDialogButtonPane.find(":tabbable")
}if(!e.length){e=this.uiDialogTitlebarClose.filter(":tabbable")
}if(!e.length){e=this.uiDialog
}e.eq(0).focus()
},_keepFocus:function(e){function f(){var h=this.document[0].activeElement,g=this.uiDialog[0]===h||c.contains(this.uiDialog[0],h);
if(!g){this._focusTabbable()
}}e.preventDefault();
f.call(this);
this._delay(f)
},_createWrapper:function(){this.uiDialog=c("<div>").addClass("ui-dialog ui-widget ui-widget-content ui-corner-all ui-front "+this.options.dialogClass).hide().attr({tabIndex:-1,role:"dialog"}).appendTo(this._appendTo());
this._on(this.uiDialog,{keydown:function(g){if(this.options.closeOnEscape&&!g.isDefaultPrevented()&&g.keyCode&&g.keyCode===c.ui.keyCode.ESCAPE){g.preventDefault();
this.close(g);
return}if(g.keyCode!==c.ui.keyCode.TAB){return
}var f=this.uiDialog.find(":tabbable"),h=f.filter(":first"),e=f.filter(":last");
if((g.target===e[0]||g.target===this.uiDialog[0])&&!g.shiftKey){h.focus(1);
g.preventDefault()
}else{if((g.target===h[0]||g.target===this.uiDialog[0])&&g.shiftKey){e.focus(1);
g.preventDefault()
}}},mousedown:function(e){if(this._moveToTop(e)){this._focusTabbable()
}}});if(!this.element.find("[aria-describedby]").length){this.uiDialog.attr({"aria-describedby":this.element.uniqueId().attr("id")})
}},_createTitlebar:function(){var e;
this.uiDialogTitlebar=c("<div>").addClass("ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix").prependTo(this.uiDialog);
this._on(this.uiDialogTitlebar,{mousedown:function(f){if(!c(f.target).closest(".ui-dialog-titlebar-close")){this.uiDialog.focus()
}}});this.uiDialogTitlebarClose=c("<button></button>").button({label:this.options.closeText,icons:{primary:"ui-icon-closethick"},text:false}).addClass("ui-dialog-titlebar-close").appendTo(this.uiDialogTitlebar);
this._on(this.uiDialogTitlebarClose,{click:function(f){f.preventDefault();
this.close(f)
}});e=c("<span>").uniqueId().addClass("ui-dialog-title").prependTo(this.uiDialogTitlebar);
this._title(e);
this.uiDialog.attr({"aria-labelledby":e.attr("id")})
},_title:function(e){if(!this.options.title){e.html("&#160;")
}e.text(this.options.title)
},_createButtonPane:function(){this.uiDialogButtonPane=c("<div>").addClass("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix");
this.uiButtonSet=c("<div>").addClass("ui-dialog-buttonset").appendTo(this.uiDialogButtonPane);
this._createButtons()
},_createButtons:function(){var f=this,e=this.options.buttons;
this.uiDialogButtonPane.remove();
this.uiButtonSet.empty();
if(c.isEmptyObject(e)||(c.isArray(e)&&!e.length)){this.uiDialog.removeClass("ui-dialog-buttons");
return}c.each(e,function(g,h){var i,j;
h=c.isFunction(h)?{click:h,text:g}:h;
h=c.extend({type:"button"},h);
i=h.click;h.click=function(){i.apply(f.element[0],arguments)
};j={icons:h.icons,text:h.showText};
delete h.icons;
delete h.showText;
c("<button></button>",h).button(j).appendTo(f.uiButtonSet)
});this.uiDialog.addClass("ui-dialog-buttons");
this.uiDialogButtonPane.appendTo(this.uiDialog)
},_makeDraggable:function(){var g=this,f=this.options;
function e(h){return{position:h.position,offset:h.offset}
}this.uiDialog.draggable({cancel:".ui-dialog-content, .ui-dialog-titlebar-close",handle:".ui-dialog-titlebar",containment:"document",start:function(h,i){c(this).addClass("ui-dialog-dragging");
g._blockFrames();
g._trigger("dragStart",h,e(i))
},drag:function(h,i){g._trigger("drag",h,e(i))
},stop:function(h,i){f.position=[i.position.left-g.document.scrollLeft(),i.position.top-g.document.scrollTop()];
c(this).removeClass("ui-dialog-dragging");
g._unblockFrames();
g._trigger("dragStop",h,e(i))
}})},_makeResizable:function(){var j=this,h=this.options,i=h.resizable,e=this.uiDialog.css("position"),g=typeof i==="string"?i:"n,e,s,w,se,sw,ne,nw";
function f(k){return{originalPosition:k.originalPosition,originalSize:k.originalSize,position:k.position,size:k.size}
}this.uiDialog.resizable({cancel:".ui-dialog-content",containment:"document",alsoResize:this.element,maxWidth:h.maxWidth,maxHeight:h.maxHeight,minWidth:h.minWidth,minHeight:this._minHeight(),handles:g,start:function(k,l){c(this).addClass("ui-dialog-resizing");
j._blockFrames();
j._trigger("resizeStart",k,f(l))
},resize:function(k,l){j._trigger("resize",k,f(l))
},stop:function(k,l){h.height=c(this).height();
h.width=c(this).width();
c(this).removeClass("ui-dialog-resizing");
j._unblockFrames();
j._trigger("resizeStop",k,f(l))
}}).css("position",e)
},_minHeight:function(){var e=this.options;
return e.height==="auto"?e.minHeight:Math.min(e.minHeight,e.height)
},_position:function(){var e=this.uiDialog.is(":visible");
if(!e){this.uiDialog.show()
}this.uiDialog.position(this.options.position);
if(!e){this.uiDialog.hide()
}},_setOptions:function(g){var h=this,f=false,e={};
c.each(g,function(i,j){h._setOption(i,j);
if(i in a){f=true
}if(i in b){e[i]=j
}});if(f){this._size();
this._position()
}if(this.uiDialog.is(":data(ui-resizable)")){this.uiDialog.resizable("option",e)
}},_setOption:function(g,h){var f,i,e=this.uiDialog;
if(g==="dialogClass"){e.removeClass(this.options.dialogClass).addClass(h)
}if(g==="disabled"){return
}this._super(g,h);
if(g==="appendTo"){this.uiDialog.appendTo(this._appendTo())
}if(g==="buttons"){this._createButtons()
}if(g==="closeText"){this.uiDialogTitlebarClose.button({label:""+h})
}if(g==="draggable"){f=e.is(":data(ui-draggable)");
if(f&&!h){e.draggable("destroy")
}if(!f&&h){this._makeDraggable()
}}if(g==="position"){this._position()
}if(g==="resizable"){i=e.is(":data(ui-resizable)");
if(i&&!h){e.resizable("destroy")
}if(i&&typeof h==="string"){e.resizable("option","handles",h)
}if(!i&&h!==false){this._makeResizable()
}}if(g==="title"){this._title(this.uiDialogTitlebar.find(".ui-dialog-title"))
}},_size:function(){var e,g,h,f=this.options;
this.element.show().css({width:"auto",minHeight:0,maxHeight:"none",height:0});
if(f.minWidth>f.width){f.width=f.minWidth
}e=this.uiDialog.css({height:"auto",width:f.width}).outerHeight();
g=Math.max(0,f.minHeight-e);
h=typeof f.maxHeight==="number"?Math.max(0,f.maxHeight-e):"none";
if(f.height==="auto"){this.element.css({minHeight:g,maxHeight:h,height:"auto"})
}else{this.element.height(Math.max(0,f.height-e))
}if(this.uiDialog.is(":data(ui-resizable)")){this.uiDialog.resizable("option","minHeight",this._minHeight())
}},_blockFrames:function(){this.iframeBlocks=this.document.find("iframe").map(function(){var e=c(this);
return c("<div>").css({position:"absolute",width:e.outerWidth(),height:e.outerHeight()}).appendTo(e.parent()).offset(e.offset())[0]
})},_unblockFrames:function(){if(this.iframeBlocks){this.iframeBlocks.remove();
delete this.iframeBlocks
}},_allowInteraction:function(e){if(c(e.target).closest(".ui-dialog").length){return true
}return !!c(e.target).closest(".ui-datepicker").length
},_createOverlay:function(){if(!this.options.modal){return
}var f=this,e=this.widgetFullName;
if(!c.ui.dialog.overlayInstances){this._delay(function(){if(c.ui.dialog.overlayInstances){this.document.bind("focusin.dialog",function(g){if(!f._allowInteraction(g)){g.preventDefault();
c(".ui-dialog:visible:last .ui-dialog-content").data(e)._focusTabbable()
}})}})}this.overlay=c("<div>").addClass("ui-widget-overlay ui-front").appendTo(this._appendTo());
this._on(this.overlay,{mousedown:"_keepFocus"});
c.ui.dialog.overlayInstances++
},_destroyOverlay:function(){if(!this.options.modal){return
}if(this.overlay){c.ui.dialog.overlayInstances--;
if(!c.ui.dialog.overlayInstances){this.document.unbind("focusin.dialog")
}this.overlay.remove();
this.overlay=null
}}});c.ui.dialog.overlayInstances=0;
if(c.uiBackCompat!==false){c.widget("ui.dialog",c.ui.dialog,{_position:function(){var f=this.options.position,g=[],h=[0,0],e;
if(f){if(typeof f==="string"||(typeof f==="object"&&"0" in f)){g=f.split?f.split(" "):[f[0],f[1]];
if(g.length===1){g[1]=g[0]
}c.each(["left","top"],function(k,j){if(+g[k]===g[k]){h[k]=g[k];
g[k]=j}});f={my:g[0]+(h[0]<0?h[0]:"+"+h[0])+" "+g[1]+(h[1]<0?h[1]:"+"+h[1]),at:g.join(" ")}
}f=c.extend({},c.ui.dialog.prototype.options.position,f)
}else{f=c.ui.dialog.prototype.options.position
}e=this.uiDialog.is(":visible");
if(!e){this.uiDialog.show()
}this.uiDialog.position(f);
if(!e){this.uiDialog.hide()
}}})}}(jQuery));
(function(a,b){a.widget("ui.menu",{version:"1.10.3",defaultElement:"<ul>",delay:300,options:{icons:{submenu:"ui-icon-carat-1-e"},menus:"ul",position:{my:"left top",at:"right top"},role:"menu",blur:null,focus:null,select:null},_create:function(){this.activeMenu=this.element;
this.mouseHandled=false;
this.element.uniqueId().addClass("ui-menu ui-widget ui-widget-content ui-corner-all").toggleClass("ui-menu-icons",!!this.element.find(".ui-icon").length).attr({role:this.options.role,tabIndex:0}).bind("click"+this.eventNamespace,a.proxy(function(c){if(this.options.disabled){c.preventDefault()
}},this));if(this.options.disabled){this.element.addClass("ui-state-disabled").attr("aria-disabled","true")
}this._on({"mousedown .ui-menu-item > a":function(c){c.preventDefault()
},"click .ui-state-disabled > a":function(c){c.preventDefault()
},"click .ui-menu-item:has(a)":function(c){var d=a(c.target).closest(".ui-menu-item");
if(!this.mouseHandled&&d.not(".ui-state-disabled").length){this.mouseHandled=true;
this.select(c);
if(d.has(".ui-menu").length){this.expand(c)
}else{if(!this.element.is(":focus")){this.element.trigger("focus",[true]);
if(this.active&&this.active.parents(".ui-menu").length===1){clearTimeout(this.timer)
}}}}},"mouseenter .ui-menu-item":function(c){var d=a(c.currentTarget);
d.siblings().children(".ui-state-active").removeClass("ui-state-active");
this.focus(c,d)
},mouseleave:"collapseAll","mouseleave .ui-menu":"collapseAll",focus:function(e,c){var d=this.active||this.element.children(".ui-menu-item").eq(0);
if(!c){this.focus(e,d)
}},blur:function(c){this._delay(function(){if(!a.contains(this.element[0],this.document[0].activeElement)){this.collapseAll(c)
}})},keydown:"_keydown"});
this.refresh();
this._on(this.document,{click:function(c){if(!a(c.target).closest(".ui-menu").length){this.collapseAll(c)
}this.mouseHandled=false
}})},_destroy:function(){this.element.removeAttr("aria-activedescendant").find(".ui-menu").addBack().removeClass("ui-menu ui-widget ui-widget-content ui-corner-all ui-menu-icons").removeAttr("role").removeAttr("tabIndex").removeAttr("aria-labelledby").removeAttr("aria-expanded").removeAttr("aria-hidden").removeAttr("aria-disabled").removeUniqueId().show();
this.element.find(".ui-menu-item").removeClass("ui-menu-item").removeAttr("role").removeAttr("aria-disabled").children("a").removeUniqueId().removeClass("ui-corner-all ui-state-hover").removeAttr("tabIndex").removeAttr("role").removeAttr("aria-haspopup").children().each(function(){var c=a(this);
if(c.data("ui-menu-submenu-carat")){c.remove()
}});this.element.find(".ui-menu-divider").removeClass("ui-menu-divider ui-widget-content")
},_keydown:function(i){var d,h,j,g,f,c=true;
function e(k){return k.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g,"\\$&")
}switch(i.keyCode){case a.ui.keyCode.PAGE_UP:this.previousPage(i);
break;case a.ui.keyCode.PAGE_DOWN:this.nextPage(i);
break;case a.ui.keyCode.HOME:this._move("first","first",i);
break;case a.ui.keyCode.END:this._move("last","last",i);
break;case a.ui.keyCode.UP:this.previous(i);
break;case a.ui.keyCode.DOWN:this.next(i);
break;case a.ui.keyCode.LEFT:this.collapse(i);
break;case a.ui.keyCode.RIGHT:if(this.active&&!this.active.is(".ui-state-disabled")){this.expand(i)
}break;case a.ui.keyCode.ENTER:case a.ui.keyCode.SPACE:this._activate(i);
break;case a.ui.keyCode.ESCAPE:this.collapse(i);
break;default:c=false;
h=this.previousFilter||"";
j=String.fromCharCode(i.keyCode);
g=false;clearTimeout(this.filterTimer);
if(j===h){g=true
}else{j=h+j
}f=new RegExp("^"+e(j),"i");
d=this.activeMenu.children(".ui-menu-item").filter(function(){return f.test(a(this).children("a").text())
});d=g&&d.index(this.active.next())!==-1?this.active.nextAll(".ui-menu-item"):d;
if(!d.length){j=String.fromCharCode(i.keyCode);
f=new RegExp("^"+e(j),"i");
d=this.activeMenu.children(".ui-menu-item").filter(function(){return f.test(a(this).children("a").text())
})}if(d.length){this.focus(i,d);
if(d.length>1){this.previousFilter=j;
this.filterTimer=this._delay(function(){delete this.previousFilter
},1000)}else{delete this.previousFilter
}}else{delete this.previousFilter
}}if(c){i.preventDefault()
}},_activate:function(c){if(!this.active.is(".ui-state-disabled")){if(this.active.children("a[aria-haspopup='true']").length){this.expand(c)
}else{this.select(c)
}}},refresh:function(){var e,d=this.options.icons.submenu,c=this.element.find(this.options.menus);
c.filter(":not(.ui-menu)").addClass("ui-menu ui-widget ui-widget-content ui-corner-all").hide().attr({role:this.options.role,"aria-hidden":"true","aria-expanded":"false"}).each(function(){var h=a(this),g=h.prev("a"),f=a("<span>").addClass("ui-menu-icon ui-icon "+d).data("ui-menu-submenu-carat",true);
g.attr("aria-haspopup","true").prepend(f);
h.attr("aria-labelledby",g.attr("id"))
});e=c.add(this.element);
e.children(":not(.ui-menu-item):has(a)").addClass("ui-menu-item").attr("role","presentation").children("a").uniqueId().addClass("ui-corner-all").attr({tabIndex:-1,role:this._itemRole()});
e.children(":not(.ui-menu-item)").each(function(){var f=a(this);
if(!/[^\-\u2014\u2013\s]/.test(f.text())){f.addClass("ui-widget-content ui-menu-divider")
}});e.children(".ui-state-disabled").attr("aria-disabled","true");
if(this.active&&!a.contains(this.element[0],this.active[0])){this.blur()
}},_itemRole:function(){return{menu:"menuitem",listbox:"option"}[this.options.role]
},_setOption:function(c,d){if(c==="icons"){this.element.find(".ui-menu-icon").removeClass(this.options.icons.submenu).addClass(d.submenu)
}this._super(c,d)
},focus:function(d,c){var f,e;
this.blur(d,d&&d.type==="focus");
this._scrollIntoView(c);
this.active=c.first();
e=this.active.children("a").addClass("ui-state-focus");
if(this.options.role){this.element.attr("aria-activedescendant",e.attr("id"))
}this.active.parent().closest(".ui-menu-item").children("a:first").addClass("ui-state-active");
if(d&&d.type==="keydown"){this._close()
}else{this.timer=this._delay(function(){this._close()
},this.delay)
}f=c.children(".ui-menu");
if(f.length&&(/^mouse/.test(d.type))){this._startOpening(f)
}this.activeMenu=c.parent();
this._trigger("focus",d,{item:c})
},_scrollIntoView:function(f){var i,e,g,c,d,h;
if(this._hasScroll()){i=parseFloat(a.css(this.activeMenu[0],"borderTopWidth"))||0;
e=parseFloat(a.css(this.activeMenu[0],"paddingTop"))||0;
g=f.offset().top-this.activeMenu.offset().top-i-e;
c=this.activeMenu.scrollTop();
d=this.activeMenu.height();
h=f.height();
if(g<0){this.activeMenu.scrollTop(c+g)
}else{if(g+h>d){this.activeMenu.scrollTop(c+g-d+h)
}}}},blur:function(d,c){if(!c){clearTimeout(this.timer)
}if(!this.active){return
}this.active.children("a").removeClass("ui-state-focus");
this.active=null;
this._trigger("blur",d,{item:this.active})
},_startOpening:function(c){clearTimeout(this.timer);
if(c.attr("aria-hidden")!=="true"){return
}this.timer=this._delay(function(){this._close();
this._open(c)
},this.delay)
},_open:function(d){var c=a.extend({of:this.active},this.options.position);
clearTimeout(this.timer);
this.element.find(".ui-menu").not(d.parents(".ui-menu")).hide().attr("aria-hidden","true");
d.show().removeAttr("aria-hidden").attr("aria-expanded","true").position(c)
},collapseAll:function(d,c){clearTimeout(this.timer);
this.timer=this._delay(function(){var e=c?this.element:a(d&&d.target).closest(this.element.find(".ui-menu"));
if(!e.length){e=this.element
}this._close(e);
this.blur(d);
this.activeMenu=e
},this.delay)
},_close:function(c){if(!c){c=this.active?this.active.parent():this.element
}c.find(".ui-menu").hide().attr("aria-hidden","true").attr("aria-expanded","false").end().find("a.ui-state-active").removeClass("ui-state-active")
},collapse:function(d){var c=this.active&&this.active.parent().closest(".ui-menu-item",this.element);
if(c&&c.length){this._close();
this.focus(d,c)
}},expand:function(d){var c=this.active&&this.active.children(".ui-menu ").children(".ui-menu-item").first();
if(c&&c.length){this._open(c.parent());
this._delay(function(){this.focus(d,c)
})}},next:function(c){this._move("next","first",c)
},previous:function(c){this._move("prev","last",c)
},isFirstItem:function(){return this.active&&!this.active.prevAll(".ui-menu-item").length
},isLastItem:function(){return this.active&&!this.active.nextAll(".ui-menu-item").length
},_move:function(f,d,e){var c;
if(this.active){if(f==="first"||f==="last"){c=this.active[f==="first"?"prevAll":"nextAll"](".ui-menu-item").eq(-1)
}else{c=this.active[f+"All"](".ui-menu-item").eq(0)
}}if(!c||!c.length||!this.active){c=this.activeMenu.children(".ui-menu-item")[d]()
}this.focus(e,c)
},nextPage:function(e){var d,f,c;
if(!this.active){this.next(e);
return}if(this.isLastItem()){return
}if(this._hasScroll()){f=this.active.offset().top;
c=this.element.height();
this.active.nextAll(".ui-menu-item").each(function(){d=a(this);
return d.offset().top-f-c<0
});this.focus(e,d)
}else{this.focus(e,this.activeMenu.children(".ui-menu-item")[!this.active?"first":"last"]())
}},previousPage:function(e){var d,f,c;
if(!this.active){this.next(e);
return}if(this.isFirstItem()){return
}if(this._hasScroll()){f=this.active.offset().top;
c=this.element.height();
this.active.prevAll(".ui-menu-item").each(function(){d=a(this);
return d.offset().top-f+c>0
});this.focus(e,d)
}else{this.focus(e,this.activeMenu.children(".ui-menu-item").first())
}},_hasScroll:function(){return this.element.outerHeight()<this.element.prop("scrollHeight")
},select:function(c){this.active=this.active||a(c.target).closest(".ui-menu-item");
var d={item:this.active};
if(!this.active.has(".ui-menu").length){this.collapseAll(c,true)
}this._trigger("select",c,d)
}})}(jQuery));
(function(a,b){a.widget("ui.progressbar",{version:"1.10.3",options:{max:100,value:0,change:null,complete:null},min:0,_create:function(){this.oldValue=this.options.value=this._constrainedValue();
this.element.addClass("ui-progressbar ui-widget ui-widget-content ui-corner-all").attr({role:"progressbar","aria-valuemin":this.min});
this.valueDiv=a("<div class='ui-progressbar-value ui-widget-header ui-corner-left'></div>").appendTo(this.element);
this._refreshValue()
},_destroy:function(){this.element.removeClass("ui-progressbar ui-widget ui-widget-content ui-corner-all").removeAttr("role").removeAttr("aria-valuemin").removeAttr("aria-valuemax").removeAttr("aria-valuenow");
this.valueDiv.remove()
},value:function(c){if(c===b){return this.options.value
}this.options.value=this._constrainedValue(c);
this._refreshValue()
},_constrainedValue:function(c){if(c===b){c=this.options.value
}this.indeterminate=c===false;
if(typeof c!=="number"){c=0
}return this.indeterminate?false:Math.min(this.options.max,Math.max(this.min,c))
},_setOptions:function(c){var d=c.value;
delete c.value;
this._super(c);
this.options.value=this._constrainedValue(d);
this._refreshValue()
},_setOption:function(c,d){if(c==="max"){d=Math.max(this.min,d)
}this._super(c,d)
},_percentage:function(){return this.indeterminate?100:100*(this.options.value-this.min)/(this.options.max-this.min)
},_refreshValue:function(){var d=this.options.value,c=this._percentage();
this.valueDiv.toggle(this.indeterminate||d>this.min).toggleClass("ui-corner-right",d===this.options.max).width(c.toFixed(0)+"%");
this.element.toggleClass("ui-progressbar-indeterminate",this.indeterminate);
if(this.indeterminate){this.element.removeAttr("aria-valuenow");
if(!this.overlayDiv){this.overlayDiv=a("<div class='ui-progressbar-overlay'></div>").appendTo(this.valueDiv)
}}else{this.element.attr({"aria-valuemax":this.options.max,"aria-valuenow":d});
if(this.overlayDiv){this.overlayDiv.remove();
this.overlayDiv=null
}}if(this.oldValue!==d){this.oldValue=d;
this._trigger("change")
}if(d===this.options.max){this._trigger("complete")
}}})})(jQuery);
(function(b,c){var a=5;
b.widget("ui.slider",b.ui.mouse,{version:"1.10.3",widgetEventPrefix:"slide",options:{animate:false,distance:0,max:100,min:0,orientation:"horizontal",range:false,step:1,value:0,values:null,change:null,slide:null,start:null,stop:null},_create:function(){this._keySliding=false;
this._mouseSliding=false;
this._animateOff=true;
this._handleIndex=null;
this._detectOrientation();
this._mouseInit();
this.element.addClass("ui-slider ui-slider-"+this.orientation+" ui-widget ui-widget-content ui-corner-all");
this._refresh();
this._setOption("disabled",this.options.disabled);
this._animateOff=false
},_refresh:function(){this._createRange();
this._createHandles();
this._setupEvents();
this._refreshValue()
},_createHandles:function(){var g,d,e=this.options,j=this.element.find(".ui-slider-handle").addClass("ui-state-default ui-corner-all"),h="<a class='ui-slider-handle ui-state-default ui-corner-all' href='#'></a>",f=[];
d=(e.values&&e.values.length)||1;
if(j.length>d){j.slice(d).remove();
j=j.slice(0,d)
}for(g=j.length;
g<d;g++){f.push(h)
}this.handles=j.add(b(f.join("")).appendTo(this.element));
this.handle=this.handles.eq(0);
this.handles.each(function(k){b(this).data("ui-slider-handle-index",k)
})},_createRange:function(){var d=this.options,e="";
if(d.range){if(d.range===true){if(!d.values){d.values=[this._valueMin(),this._valueMin()]
}else{if(d.values.length&&d.values.length!==2){d.values=[d.values[0],d.values[0]]
}else{if(b.isArray(d.values)){d.values=d.values.slice(0)
}}}}if(!this.range||!this.range.length){this.range=b("<div></div>").appendTo(this.element);
e="ui-slider-range ui-widget-header ui-corner-all"
}else{this.range.removeClass("ui-slider-range-min ui-slider-range-max").css({left:"",bottom:""})
}this.range.addClass(e+((d.range==="min"||d.range==="max")?" ui-slider-range-"+d.range:""))
}else{this.range=b([])
}},_setupEvents:function(){var d=this.handles.add(this.range).filter("a");
this._off(d);
this._on(d,this._handleEvents);
this._hoverable(d);
this._focusable(d)
},_destroy:function(){this.handles.remove();
this.range.remove();
this.element.removeClass("ui-slider ui-slider-horizontal ui-slider-vertical ui-widget ui-widget-content ui-corner-all");
this._mouseDestroy()
},_mouseCapture:function(f){var j,m,e,h,l,n,i,d,k=this,g=this.options;
if(g.disabled){return false
}this.elementSize={width:this.element.outerWidth(),height:this.element.outerHeight()};
this.elementOffset=this.element.offset();
j={x:f.pageX,y:f.pageY};
m=this._normValueFromMouse(j);
e=this._valueMax()-this._valueMin()+1;
this.handles.each(function(o){var p=Math.abs(m-k.values(o));
if((e>p)||(e===p&&(o===k._lastChangedValue||k.values(o)===g.min))){e=p;
h=b(this);l=o
}});n=this._start(f,l);
if(n===false){return false
}this._mouseSliding=true;
this._handleIndex=l;
h.addClass("ui-state-active").focus();
i=h.offset();
d=!b(f.target).parents().addBack().is(".ui-slider-handle");
this._clickOffset=d?{left:0,top:0}:{left:f.pageX-i.left-(h.width()/2),top:f.pageY-i.top-(h.height()/2)-(parseInt(h.css("borderTopWidth"),10)||0)-(parseInt(h.css("borderBottomWidth"),10)||0)+(parseInt(h.css("marginTop"),10)||0)};
if(!this.handles.hasClass("ui-state-hover")){this._slide(f,l,m)
}this._animateOff=true;
return true
},_mouseStart:function(){return true
},_mouseDrag:function(f){var d={x:f.pageX,y:f.pageY},e=this._normValueFromMouse(d);
this._slide(f,this._handleIndex,e);
return false
},_mouseStop:function(d){this.handles.removeClass("ui-state-active");
this._mouseSliding=false;
this._stop(d,this._handleIndex);
this._change(d,this._handleIndex);
this._handleIndex=null;
this._clickOffset=null;
this._animateOff=false;
return false
},_detectOrientation:function(){this.orientation=(this.options.orientation==="vertical")?"vertical":"horizontal"
},_normValueFromMouse:function(e){var d,h,g,f,i;
if(this.orientation==="horizontal"){d=this.elementSize.width;
h=e.x-this.elementOffset.left-(this._clickOffset?this._clickOffset.left:0)
}else{d=this.elementSize.height;
h=e.y-this.elementOffset.top-(this._clickOffset?this._clickOffset.top:0)
}g=(h/d);if(g>1){g=1
}if(g<0){g=0
}if(this.orientation==="vertical"){g=1-g
}f=this._valueMax()-this._valueMin();
i=this._valueMin()+g*f;
return this._trimAlignValue(i)
},_start:function(f,e){var d={handle:this.handles[e],value:this.value()};
if(this.options.values&&this.options.values.length){d.value=this.values(e);
d.values=this.values()
}return this._trigger("start",f,d)
},_slide:function(h,g,f){var d,e,i;
if(this.options.values&&this.options.values.length){d=this.values(g?0:1);
if((this.options.values.length===2&&this.options.range===true)&&((g===0&&f>d)||(g===1&&f<d))){f=d
}if(f!==this.values(g)){e=this.values();
e[g]=f;i=this._trigger("slide",h,{handle:this.handles[g],value:f,values:e});
d=this.values(g?0:1);
if(i!==false){this.values(g,f,true)
}}}else{if(f!==this.value()){i=this._trigger("slide",h,{handle:this.handles[g],value:f});
if(i!==false){this.value(f)
}}}},_stop:function(f,e){var d={handle:this.handles[e],value:this.value()};
if(this.options.values&&this.options.values.length){d.value=this.values(e);
d.values=this.values()
}this._trigger("stop",f,d)
},_change:function(f,e){if(!this._keySliding&&!this._mouseSliding){var d={handle:this.handles[e],value:this.value()};
if(this.options.values&&this.options.values.length){d.value=this.values(e);
d.values=this.values()
}this._lastChangedValue=e;
this._trigger("change",f,d)
}},value:function(d){if(arguments.length){this.options.value=this._trimAlignValue(d);
this._refreshValue();
this._change(null,0);
return}return this._value()
},values:function(e,h){var g,d,f;
if(arguments.length>1){this.options.values[e]=this._trimAlignValue(h);
this._refreshValue();
this._change(null,e);
return}if(arguments.length){if(b.isArray(arguments[0])){g=this.options.values;
d=arguments[0];
for(f=0;f<g.length;
f+=1){g[f]=this._trimAlignValue(d[f]);
this._change(null,f)
}this._refreshValue()
}else{if(this.options.values&&this.options.values.length){return this._values(e)
}else{return this.value()
}}}else{return this._values()
}},_setOption:function(e,f){var d,g=0;
if(e==="range"&&this.options.range===true){if(f==="min"){this.options.value=this._values(0);
this.options.values=null
}else{if(f==="max"){this.options.value=this._values(this.options.values.length-1);
this.options.values=null
}}}if(b.isArray(this.options.values)){g=this.options.values.length
}b.Widget.prototype._setOption.apply(this,arguments);
switch(e){case"orientation":this._detectOrientation();
this.element.removeClass("ui-slider-horizontal ui-slider-vertical").addClass("ui-slider-"+this.orientation);
this._refreshValue();
break;case"value":this._animateOff=true;
this._refreshValue();
this._change(null,0);
this._animateOff=false;
break;case"values":this._animateOff=true;
this._refreshValue();
for(d=0;d<g;
d+=1){this._change(null,d)
}this._animateOff=false;
break;case"min":case"max":this._animateOff=true;
this._refreshValue();
this._animateOff=false;
break;case"range":this._animateOff=true;
this._refresh();
this._animateOff=false;
break}},_value:function(){var d=this.options.value;
d=this._trimAlignValue(d);
return d},_values:function(d){var g,f,e;
if(arguments.length){g=this.options.values[d];
g=this._trimAlignValue(g);
return g}else{if(this.options.values&&this.options.values.length){f=this.options.values.slice();
for(e=0;e<f.length;
e+=1){f[e]=this._trimAlignValue(f[e])
}return f}else{return[]
}}},_trimAlignValue:function(g){if(g<=this._valueMin()){return this._valueMin()
}if(g>=this._valueMax()){return this._valueMax()
}var d=(this.options.step>0)?this.options.step:1,f=(g-this._valueMin())%d,e=g-f;
if(Math.abs(f)*2>=d){e+=(f>0)?d:(-d)
}return parseFloat(e.toFixed(5))
},_valueMin:function(){return this.options.min
},_valueMax:function(){return this.options.max
},_refreshValue:function(){var i,h,l,j,m,g=this.options.range,f=this.options,k=this,e=(!this._animateOff)?f.animate:false,d={};
if(this.options.values&&this.options.values.length){this.handles.each(function(n){h=(k.values(n)-k._valueMin())/(k._valueMax()-k._valueMin())*100;
d[k.orientation==="horizontal"?"left":"bottom"]=h+"%";
b(this).stop(1,1)[e?"animate":"css"](d,f.animate);
if(k.options.range===true){if(k.orientation==="horizontal"){if(n===0){k.range.stop(1,1)[e?"animate":"css"]({left:h+"%"},f.animate)
}if(n===1){k.range[e?"animate":"css"]({width:(h-i)+"%"},{queue:false,duration:f.animate})
}}else{if(n===0){k.range.stop(1,1)[e?"animate":"css"]({bottom:(h)+"%"},f.animate)
}if(n===1){k.range[e?"animate":"css"]({height:(h-i)+"%"},{queue:false,duration:f.animate})
}}}i=h})}else{l=this.value();
j=this._valueMin();
m=this._valueMax();
h=(m!==j)?(l-j)/(m-j)*100:0;
d[this.orientation==="horizontal"?"left":"bottom"]=h+"%";
this.handle.stop(1,1)[e?"animate":"css"](d,f.animate);
if(g==="min"&&this.orientation==="horizontal"){this.range.stop(1,1)[e?"animate":"css"]({width:h+"%"},f.animate)
}if(g==="max"&&this.orientation==="horizontal"){this.range[e?"animate":"css"]({width:(100-h)+"%"},{queue:false,duration:f.animate})
}if(g==="min"&&this.orientation==="vertical"){this.range.stop(1,1)[e?"animate":"css"]({height:h+"%"},f.animate)
}if(g==="max"&&this.orientation==="vertical"){this.range[e?"animate":"css"]({height:(100-h)+"%"},{queue:false,duration:f.animate})
}}},_handleEvents:{keydown:function(h){var i,f,e,g,d=b(h.target).data("ui-slider-handle-index");
switch(h.keyCode){case b.ui.keyCode.HOME:case b.ui.keyCode.END:case b.ui.keyCode.PAGE_UP:case b.ui.keyCode.PAGE_DOWN:case b.ui.keyCode.UP:case b.ui.keyCode.RIGHT:case b.ui.keyCode.DOWN:case b.ui.keyCode.LEFT:h.preventDefault();
if(!this._keySliding){this._keySliding=true;
b(h.target).addClass("ui-state-active");
i=this._start(h,d);
if(i===false){return
}}break}g=this.options.step;
if(this.options.values&&this.options.values.length){f=e=this.values(d)
}else{f=e=this.value()
}switch(h.keyCode){case b.ui.keyCode.HOME:e=this._valueMin();
break;case b.ui.keyCode.END:e=this._valueMax();
break;case b.ui.keyCode.PAGE_UP:e=this._trimAlignValue(f+((this._valueMax()-this._valueMin())/a));
break;case b.ui.keyCode.PAGE_DOWN:e=this._trimAlignValue(f-((this._valueMax()-this._valueMin())/a));
break;case b.ui.keyCode.UP:case b.ui.keyCode.RIGHT:if(f===this._valueMax()){return
}e=this._trimAlignValue(f+g);
break;case b.ui.keyCode.DOWN:case b.ui.keyCode.LEFT:if(f===this._valueMin()){return
}e=this._trimAlignValue(f-g);
break}this._slide(h,d,e)
},click:function(d){d.preventDefault()
},keyup:function(e){var d=b(e.target).data("ui-slider-handle-index");
if(this._keySliding){this._keySliding=false;
this._stop(e,d);
this._change(e,d);
b(e.target).removeClass("ui-state-active")
}}}})}(jQuery));
(function(b){function a(c){return function(){var d=this.element.val();
c.apply(this,arguments);
this._refresh();
if(d!==this.element.val()){this._trigger("change")
}}}b.widget("ui.spinner",{version:"1.10.3",defaultElement:"<input>",widgetEventPrefix:"spin",options:{culture:null,icons:{down:"ui-icon-triangle-1-s",up:"ui-icon-triangle-1-n"},incremental:true,max:null,min:null,numberFormat:null,page:10,step:1,change:null,spin:null,start:null,stop:null},_create:function(){this._setOption("max",this.options.max);
this._setOption("min",this.options.min);
this._setOption("step",this.options.step);
this._value(this.element.val(),true);
this._draw();
this._on(this._events);
this._refresh();
this._on(this.window,{beforeunload:function(){this.element.removeAttr("autocomplete")
}})},_getCreateOptions:function(){var c={},d=this.element;
b.each(["min","max","step"],function(e,f){var g=d.attr(f);
if(g!==undefined&&g.length){c[f]=g
}});return c
},_events:{keydown:function(c){if(this._start(c)&&this._keydown(c)){c.preventDefault()
}},keyup:"_stop",focus:function(){this.previous=this.element.val()
},blur:function(c){if(this.cancelBlur){delete this.cancelBlur;
return}this._stop();
this._refresh();
if(this.previous!==this.element.val()){this._trigger("change",c)
}},mousewheel:function(c,d){if(!d){return
}if(!this.spinning&&!this._start(c)){return false
}this._spin((d>0?1:-1)*this.options.step,c);
clearTimeout(this.mousewheelTimer);
this.mousewheelTimer=this._delay(function(){if(this.spinning){this._stop(c)
}},100);c.preventDefault()
},"mousedown .ui-spinner-button":function(d){var c;
c=this.element[0]===this.document[0].activeElement?this.previous:this.element.val();
function e(){var f=this.element[0]===this.document[0].activeElement;
if(!f){this.element.focus();
this.previous=c;
this._delay(function(){this.previous=c
})}}d.preventDefault();
e.call(this);
this.cancelBlur=true;
this._delay(function(){delete this.cancelBlur;
e.call(this)
});if(this._start(d)===false){return
}this._repeat(null,b(d.currentTarget).hasClass("ui-spinner-up")?1:-1,d)
},"mouseup .ui-spinner-button":"_stop","mouseenter .ui-spinner-button":function(c){if(!b(c.currentTarget).hasClass("ui-state-active")){return
}if(this._start(c)===false){return false
}this._repeat(null,b(c.currentTarget).hasClass("ui-spinner-up")?1:-1,c)
},"mouseleave .ui-spinner-button":"_stop"},_draw:function(){var c=this.uiSpinner=this.element.addClass("ui-spinner-input").attr("autocomplete","off").wrap(this._uiSpinnerHtml()).parent().append(this._buttonHtml());
this.element.attr("role","spinbutton");
this.buttons=c.find(".ui-spinner-button").attr("tabIndex",-1).button().removeClass("ui-corner-all");
if(this.buttons.height()>Math.ceil(c.height()*0.5)&&c.height()>0){c.height(c.height())
}if(this.options.disabled){this.disable()
}},_keydown:function(d){var c=this.options,e=b.ui.keyCode;
switch(d.keyCode){case e.UP:this._repeat(null,1,d);
return true;
case e.DOWN:this._repeat(null,-1,d);
return true;
case e.PAGE_UP:this._repeat(null,c.page,d);
return true;
case e.PAGE_DOWN:this._repeat(null,-c.page,d);
return true
}return false
},_uiSpinnerHtml:function(){return"<span class='ui-spinner ui-widget ui-widget-content ui-corner-all'></span>"
},_buttonHtml:function(){return"<a class='ui-spinner-button ui-spinner-up ui-corner-tr'><span class='ui-icon "+this.options.icons.up+"'>&#9650;</span></a><a class='ui-spinner-button ui-spinner-down ui-corner-br'><span class='ui-icon "+this.options.icons.down+"'>&#9660;</span></a>"
},_start:function(c){if(!this.spinning&&this._trigger("start",c)===false){return false
}if(!this.counter){this.counter=1
}this.spinning=true;
return true
},_repeat:function(d,c,e){d=d||500;
clearTimeout(this.timer);
this.timer=this._delay(function(){this._repeat(40,c,e)
},d);this._spin(c*this.options.step,e)
},_spin:function(d,c){var e=this.value()||0;
if(!this.counter){this.counter=1
}e=this._adjustValue(e+d*this._increment(this.counter));
if(!this.spinning||this._trigger("spin",c,{value:e})!==false){this._value(e);
this.counter++
}},_increment:function(c){var d=this.options.incremental;
if(d){return b.isFunction(d)?d(c):Math.floor(c*c*c/50000-c*c/500+17*c/200+1)
}return 1},_precision:function(){var c=this._precisionOf(this.options.step);
if(this.options.min!==null){c=Math.max(c,this._precisionOf(this.options.min))
}return c},_precisionOf:function(d){var e=d.toString(),c=e.indexOf(".");
return c===-1?0:e.length-c-1
},_adjustValue:function(e){var d,f,c=this.options;
d=c.min!==null?c.min:0;
f=e-d;f=Math.round(f/c.step)*c.step;
e=d+f;e=parseFloat(e.toFixed(this._precision()));
if(c.max!==null&&e>c.max){return c.max
}if(c.min!==null&&e<c.min){return c.min
}return e},_stop:function(c){if(!this.spinning){return
}clearTimeout(this.timer);
clearTimeout(this.mousewheelTimer);
this.counter=0;
this.spinning=false;
this._trigger("stop",c)
},_setOption:function(c,d){if(c==="culture"||c==="numberFormat"){var e=this._parse(this.element.val());
this.options[c]=d;
this.element.val(this._format(e));
return}if(c==="max"||c==="min"||c==="step"){if(typeof d==="string"){d=this._parse(d)
}}if(c==="icons"){this.buttons.first().find(".ui-icon").removeClass(this.options.icons.up).addClass(d.up);
this.buttons.last().find(".ui-icon").removeClass(this.options.icons.down).addClass(d.down)
}this._super(c,d);
if(c==="disabled"){if(d){this.element.prop("disabled",true);
this.buttons.button("disable")
}else{this.element.prop("disabled",false);
this.buttons.button("enable")
}}},_setOptions:a(function(c){this._super(c);
this._value(this.element.val())
}),_parse:function(c){if(typeof c==="string"&&c!==""){c=window.Globalize&&this.options.numberFormat?Globalize.parseFloat(c,10,this.options.culture):+c
}return c===""||isNaN(c)?null:c
},_format:function(c){if(c===""){return""
}return window.Globalize&&this.options.numberFormat?Globalize.format(c,this.options.numberFormat,this.options.culture):c
},_refresh:function(){this.element.attr({"aria-valuemin":this.options.min,"aria-valuemax":this.options.max,"aria-valuenow":this._parse(this.element.val())})
},_value:function(e,c){var d;
if(e!==""){d=this._parse(e);
if(d!==null){if(!c){d=this._adjustValue(d)
}e=this._format(d)
}}this.element.val(e);
this._refresh()
},_destroy:function(){this.element.removeClass("ui-spinner-input").prop("disabled",false).removeAttr("autocomplete").removeAttr("role").removeAttr("aria-valuemin").removeAttr("aria-valuemax").removeAttr("aria-valuenow");
this.uiSpinner.replaceWith(this.element)
},stepUp:a(function(c){this._stepUp(c)
}),_stepUp:function(c){if(this._start()){this._spin((c||1)*this.options.step);
this._stop()
}},stepDown:a(function(c){this._stepDown(c)
}),_stepDown:function(c){if(this._start()){this._spin((c||1)*-this.options.step);
this._stop()
}},pageUp:a(function(c){this._stepUp((c||1)*this.options.page)
}),pageDown:a(function(c){this._stepDown((c||1)*this.options.page)
}),value:function(c){if(!arguments.length){return this._parse(this.element.val())
}a(this._value).call(this,c)
},widget:function(){return this.uiSpinner
}})}(jQuery));
(function(c,e){var a=0,f=/#.*$/;
function d(){return ++a
}function b(g){return g.hash.length>1&&decodeURIComponent(g.href.replace(f,""))===decodeURIComponent(location.href.replace(f,""))
}c.widget("ui.tabs",{version:"1.10.3",delay:300,options:{active:null,collapsible:false,event:"click",heightStyle:"content",hide:null,show:null,activate:null,beforeActivate:null,beforeLoad:null,load:null},_create:function(){var h=this,g=this.options;
this.running=false;
this.element.addClass("ui-tabs ui-widget ui-widget-content ui-corner-all").toggleClass("ui-tabs-collapsible",g.collapsible).delegate(".ui-tabs-nav > li","mousedown"+this.eventNamespace,function(i){if(c(this).is(".ui-state-disabled")){i.preventDefault()
}}).delegate(".ui-tabs-anchor","focus"+this.eventNamespace,function(){if(c(this).closest("li").is(".ui-state-disabled")){this.blur()
}});this._processTabs();
g.active=this._initialActive();
if(c.isArray(g.disabled)){g.disabled=c.unique(g.disabled.concat(c.map(this.tabs.filter(".ui-state-disabled"),function(i){return h.tabs.index(i)
}))).sort()
}if(this.options.active!==false&&this.anchors.length){this.active=this._findActive(g.active)
}else{this.active=c()
}this._refresh();
if(this.active.length){this.load(g.active)
}},_initialActive:function(){var h=this.options.active,g=this.options.collapsible,i=location.hash.substring(1);
if(h===null){if(i){this.tabs.each(function(j,k){if(c(k).attr("aria-controls")===i){h=j;
return false
}})}if(h===null){h=this.tabs.index(this.tabs.filter(".ui-tabs-active"))
}if(h===null||h===-1){h=this.tabs.length?0:false
}}if(h!==false){h=this.tabs.index(this.tabs.eq(h));
if(h===-1){h=g?false:0
}}if(!g&&h===false&&this.anchors.length){h=0
}return h},_getCreateEventData:function(){return{tab:this.active,panel:!this.active.length?c():this._getPanelForTab(this.active)}
},_tabKeydown:function(i){var h=c(this.document[0].activeElement).closest("li"),g=this.tabs.index(h),j=true;
if(this._handlePageNav(i)){return
}switch(i.keyCode){case c.ui.keyCode.RIGHT:case c.ui.keyCode.DOWN:g++;
break;case c.ui.keyCode.UP:case c.ui.keyCode.LEFT:j=false;
g--;break;case c.ui.keyCode.END:g=this.anchors.length-1;
break;case c.ui.keyCode.HOME:g=0;
break;case c.ui.keyCode.SPACE:i.preventDefault();
clearTimeout(this.activating);
this._activate(g);
return;case c.ui.keyCode.ENTER:i.preventDefault();
clearTimeout(this.activating);
this._activate(g===this.options.active?false:g);
return;default:return
}i.preventDefault();
clearTimeout(this.activating);
g=this._focusNextTab(g,j);
if(!i.ctrlKey){h.attr("aria-selected","false");
this.tabs.eq(g).attr("aria-selected","true");
this.activating=this._delay(function(){this.option("active",g)
},this.delay)
}},_panelKeydown:function(g){if(this._handlePageNav(g)){return
}if(g.ctrlKey&&g.keyCode===c.ui.keyCode.UP){g.preventDefault();
this.active.focus()
}},_handlePageNav:function(g){if(g.altKey&&g.keyCode===c.ui.keyCode.PAGE_UP){this._activate(this._focusNextTab(this.options.active-1,false));
return true
}if(g.altKey&&g.keyCode===c.ui.keyCode.PAGE_DOWN){this._activate(this._focusNextTab(this.options.active+1,true));
return true
}},_findNextTab:function(h,i){var g=this.tabs.length-1;
function j(){if(h>g){h=0
}if(h<0){h=g
}return h}while(c.inArray(j(),this.options.disabled)!==-1){h=i?h+1:h-1
}return h},_focusNextTab:function(g,h){g=this._findNextTab(g,h);
this.tabs.eq(g).focus();
return g},_setOption:function(g,h){if(g==="active"){this._activate(h);
return}if(g==="disabled"){this._setupDisabled(h);
return}this._super(g,h);
if(g==="collapsible"){this.element.toggleClass("ui-tabs-collapsible",h);
if(!h&&this.options.active===false){this._activate(0)
}}if(g==="event"){this._setupEvents(h)
}if(g==="heightStyle"){this._setupHeightStyle(h)
}},_tabId:function(g){return g.attr("aria-controls")||"ui-tabs-"+d()
},_sanitizeSelector:function(g){return g?g.replace(/[!"$%&'()*+,.\/:;<=>?@\[\]\^`{|}~]/g,"\\$&"):""
},refresh:function(){var h=this.options,g=this.tablist.children(":has(a[href])");
h.disabled=c.map(g.filter(".ui-state-disabled"),function(i){return g.index(i)
});this._processTabs();
if(h.active===false||!this.anchors.length){h.active=false;
this.active=c()
}else{if(this.active.length&&!c.contains(this.tablist[0],this.active[0])){if(this.tabs.length===h.disabled.length){h.active=false;
this.active=c()
}else{this._activate(this._findNextTab(Math.max(0,h.active-1),false))
}}else{h.active=this.tabs.index(this.active)
}}this._refresh()
},_refresh:function(){this._setupDisabled(this.options.disabled);
this._setupEvents(this.options.event);
this._setupHeightStyle(this.options.heightStyle);
this.tabs.not(this.active).attr({"aria-selected":"false",tabIndex:-1});
this.panels.not(this._getPanelForTab(this.active)).hide().attr({"aria-expanded":"false","aria-hidden":"true"});
if(!this.active.length){this.tabs.eq(0).attr("tabIndex",0)
}else{this.active.addClass("ui-tabs-active ui-state-active").attr({"aria-selected":"true",tabIndex:0});
this._getPanelForTab(this.active).show().attr({"aria-expanded":"true","aria-hidden":"false"})
}},_processTabs:function(){var g=this;
this.tablist=this._getList().addClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all").attr("role","tablist");
this.tabs=this.tablist.find("> li:has(a[href])").addClass("ui-state-default ui-corner-top").attr({role:"tab",tabIndex:-1});
this.anchors=this.tabs.map(function(){return c("a",this)[0]
}).addClass("ui-tabs-anchor").attr({role:"presentation",tabIndex:-1});
this.panels=c();
this.anchors.each(function(n,l){var h,j,m,k=c(l).uniqueId().attr("id"),o=c(l).closest("li"),p=o.attr("aria-controls");
if(b(l)){h=l.hash;
j=g.element.find(g._sanitizeSelector(h))
}else{m=g._tabId(o);
h="#"+m;j=g.element.find(h);
if(!j.length){j=g._createPanel(m);
j.insertAfter(g.panels[n-1]||g.tablist)
}j.attr("aria-live","polite")
}if(j.length){g.panels=g.panels.add(j)
}if(p){o.data("ui-tabs-aria-controls",p)
}o.attr({"aria-controls":h.substring(1),"aria-labelledby":k});
j.attr("aria-labelledby",k)
});this.panels.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom").attr("role","tabpanel")
},_getList:function(){return this.element.find("ol,ul").eq(0)
},_createPanel:function(g){return c("<div>").attr("id",g).addClass("ui-tabs-panel ui-widget-content ui-corner-bottom").data("ui-tabs-destroy",true)
},_setupDisabled:function(j){if(c.isArray(j)){if(!j.length){j=false
}else{if(j.length===this.anchors.length){j=true
}}}for(var h=0,g;
(g=this.tabs[h]);
h++){if(j===true||c.inArray(h,j)!==-1){c(g).addClass("ui-state-disabled").attr("aria-disabled","true")
}else{c(g).removeClass("ui-state-disabled").removeAttr("aria-disabled")
}}this.options.disabled=j
},_setupEvents:function(h){var g={click:function(i){i.preventDefault()
}};if(h){c.each(h.split(" "),function(j,i){g[i]="_eventHandler"
})}this._off(this.anchors.add(this.tabs).add(this.panels));
this._on(this.anchors,g);
this._on(this.tabs,{keydown:"_tabKeydown"});
this._on(this.panels,{keydown:"_panelKeydown"});
this._focusable(this.tabs);
this._hoverable(this.tabs)
},_setupHeightStyle:function(g){var i,h=this.element.parent();
if(g==="fill"){i=h.height();
i-=this.element.outerHeight()-this.element.height();
this.element.siblings(":visible").each(function(){var k=c(this),j=k.css("position");
if(j==="absolute"||j==="fixed"){return
}i-=k.outerHeight(true)
});this.element.children().not(this.panels).each(function(){i-=c(this).outerHeight(true)
});this.panels.each(function(){c(this).height(Math.max(0,i-c(this).innerHeight()+c(this).height()))
}).css("overflow","auto")
}else{if(g==="auto"){i=0;
this.panels.each(function(){i=Math.max(i,c(this).height("").height())
}).height(i)
}}},_eventHandler:function(g){var p=this.options,k=this.active,l=c(g.currentTarget),j=l.closest("li"),n=j[0]===k[0],h=n&&p.collapsible,i=h?c():this._getPanelForTab(j),m=!k.length?c():this._getPanelForTab(k),o={oldTab:k,oldPanel:m,newTab:h?c():j,newPanel:i};
g.preventDefault();
if(j.hasClass("ui-state-disabled")||j.hasClass("ui-tabs-loading")||this.running||(n&&!p.collapsible)||(this._trigger("beforeActivate",g,o)===false)){return
}p.active=h?false:this.tabs.index(j);
this.active=n?c():j;
if(this.xhr){this.xhr.abort()
}if(!m.length&&!i.length){c.error("jQuery UI Tabs: Mismatching fragment identifier.")
}if(i.length){this.load(this.tabs.index(j),g)
}this._toggle(g,o)
},_toggle:function(m,l){var k=this,g=l.newPanel,j=l.oldPanel;
this.running=true;
function i(){k.running=false;
k._trigger("activate",m,l)
}function h(){l.newTab.closest("li").addClass("ui-tabs-active ui-state-active");
if(g.length&&k.options.show){k._show(g,k.options.show,i)
}else{g.show();
i()}}if(j.length&&this.options.hide){this._hide(j,this.options.hide,function(){l.oldTab.closest("li").removeClass("ui-tabs-active ui-state-active");
h()})}else{l.oldTab.closest("li").removeClass("ui-tabs-active ui-state-active");
j.hide();h()
}j.attr({"aria-expanded":"false","aria-hidden":"true"});
l.oldTab.attr("aria-selected","false");
if(g.length&&j.length){l.oldTab.attr("tabIndex",-1)
}else{if(g.length){this.tabs.filter(function(){return c(this).attr("tabIndex")===0
}).attr("tabIndex",-1)
}}g.attr({"aria-expanded":"true","aria-hidden":"false"});
l.newTab.attr({"aria-selected":"true",tabIndex:0})
},_activate:function(h){var g,i=this._findActive(h);
if(i[0]===this.active[0]){return
}if(!i.length){i=this.active
}g=i.find(".ui-tabs-anchor")[0];
this._eventHandler({target:g,currentTarget:g,preventDefault:c.noop})
},_findActive:function(g){return g===false?c():this.tabs.eq(g)
},_getIndex:function(g){if(typeof g==="string"){g=this.anchors.index(this.anchors.filter("[href$='"+g+"']"))
}return g},_destroy:function(){if(this.xhr){this.xhr.abort()
}this.element.removeClass("ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible");
this.tablist.removeClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all").removeAttr("role");
this.anchors.removeClass("ui-tabs-anchor").removeAttr("role").removeAttr("tabIndex").removeUniqueId();
this.tabs.add(this.panels).each(function(){if(c.data(this,"ui-tabs-destroy")){c(this).remove()
}else{c(this).removeClass("ui-state-default ui-state-active ui-state-disabled ui-corner-top ui-corner-bottom ui-widget-content ui-tabs-active ui-tabs-panel").removeAttr("tabIndex").removeAttr("aria-live").removeAttr("aria-busy").removeAttr("aria-selected").removeAttr("aria-labelledby").removeAttr("aria-hidden").removeAttr("aria-expanded").removeAttr("role")
}});this.tabs.each(function(){var g=c(this),h=g.data("ui-tabs-aria-controls");
if(h){g.attr("aria-controls",h).removeData("ui-tabs-aria-controls")
}else{g.removeAttr("aria-controls")
}});this.panels.show();
if(this.options.heightStyle!=="content"){this.panels.css("height","")
}},enable:function(g){var h=this.options.disabled;
if(h===false){return
}if(g===e){h=false
}else{g=this._getIndex(g);
if(c.isArray(h)){h=c.map(h,function(i){return i!==g?i:null
})}else{h=c.map(this.tabs,function(i,j){return j!==g?j:null
})}}this._setupDisabled(h)
},disable:function(g){var h=this.options.disabled;
if(h===true){return
}if(g===e){h=true
}else{g=this._getIndex(g);
if(c.inArray(g,h)!==-1){return
}if(c.isArray(h)){h=c.merge([g],h).sort()
}else{h=[g]
}}this._setupDisabled(h)
},load:function(i,m){i=this._getIndex(i);
var l=this,j=this.tabs.eq(i),h=j.find(".ui-tabs-anchor"),g=this._getPanelForTab(j),k={tab:j,panel:g};
if(b(h[0])){return
}this.xhr=c.ajax(this._ajaxSettings(h,m,k));
if(this.xhr&&this.xhr.statusText!=="canceled"){j.addClass("ui-tabs-loading");
g.attr("aria-busy","true");
this.xhr.success(function(n){setTimeout(function(){g.html(n);
l._trigger("load",m,k)
},1)}).complete(function(o,n){setTimeout(function(){if(n==="abort"){l.panels.stop(false,true)
}j.removeClass("ui-tabs-loading");
g.removeAttr("aria-busy");
if(o===l.xhr){delete l.xhr
}},1)})}},_ajaxSettings:function(g,j,i){var h=this;
return{url:g.attr("href"),beforeSend:function(l,k){return h._trigger("beforeLoad",j,c.extend({jqXHR:l,ajaxSettings:k},i))
}}},_getPanelForTab:function(g){var h=c(g).attr("aria-controls");
return this.element.find(this._sanitizeSelector("#"+h))
}})})(jQuery);
(function(d){var b=0;
function c(f,g){var e=(f.attr("aria-describedby")||"").split(/\s+/);
e.push(g);f.data("ui-tooltip-id",g).attr("aria-describedby",d.trim(e.join(" ")))
}function a(g){var h=g.data("ui-tooltip-id"),f=(g.attr("aria-describedby")||"").split(/\s+/),e=d.inArray(h,f);
if(e!==-1){f.splice(e,1)
}g.removeData("ui-tooltip-id");
f=d.trim(f.join(" "));
if(f){g.attr("aria-describedby",f)
}else{g.removeAttr("aria-describedby")
}}}(jQuery));
(function(a,c){var b="ui-effects-";
a.effects={effect:{}};
/*
 * jQuery Color Animations v2.1.2
 * https://github.com/jquery/jquery-color
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * Date: Wed Jan 16 08:47:09 2013 -0600
 */
(function(r,g){var n="backgroundColor borderBottomColor borderLeftColor borderRightColor borderTopColor color columnRuleColor outlineColor textDecorationColor textEmphasisColor",k=/^([\-+])=\s*(\d+\.?\d*)/,j=[{re:/rgba?\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,parse:function(s){return[s[1],s[2],s[3],s[4]]
}},{re:/rgba?\(\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,parse:function(s){return[s[1]*2.55,s[2]*2.55,s[3]*2.55,s[4]]
}},{re:/#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})/,parse:function(s){return[parseInt(s[1],16),parseInt(s[2],16),parseInt(s[3],16)]
}},{re:/#([a-f0-9])([a-f0-9])([a-f0-9])/,parse:function(s){return[parseInt(s[1]+s[1],16),parseInt(s[2]+s[2],16),parseInt(s[3]+s[3],16)]
}},{re:/hsla?\(\s*(\d+(?:\.\d+)?)\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,space:"hsla",parse:function(s){return[s[1],s[2]/100,s[3]/100,s[4]]
}}],h=r.Color=function(t,u,s,v){return new r.Color.fn.parse(t,u,s,v)
},m={rgba:{props:{red:{idx:0,type:"byte"},green:{idx:1,type:"byte"},blue:{idx:2,type:"byte"}}},hsla:{props:{hue:{idx:0,type:"degrees"},saturation:{idx:1,type:"percent"},lightness:{idx:2,type:"percent"}}}},q={"byte":{floor:true,max:255},percent:{max:1},degrees:{mod:360,floor:true}},p=h.support={},e=r("<p>")[0],d,o=r.each;
e.style.cssText="background-color:rgba(1,1,1,.5)";
p.rgba=e.style.backgroundColor.indexOf("rgba")>-1;
o(m,function(s,t){t.cache="_"+s;
t.props.alpha={idx:3,type:"percent",def:1}
});function l(t,v,u){var s=q[v.type]||{};
if(t==null){return(u||!v.def)?null:v.def
}t=s.floor?~~t:parseFloat(t);
if(isNaN(t)){return v.def
}if(s.mod){return(t+s.mod)%s.mod
}return 0>t?0:s.max<t?s.max:t
}function i(s){var u=h(),t=u._rgba=[];
s=s.toLowerCase();
o(j,function(z,A){var x,y=A.re.exec(s),w=y&&A.parse(y),v=A.space||"rgba";
if(w){x=u[v](w);
u[m[v].cache]=x[m[v].cache];
t=u._rgba=x._rgba;
return false
}});if(t.length){if(t.join()==="0,0,0,0"){r.extend(t,d.transparent)
}return u}return d[s]
}h.fn=r.extend(h.prototype,{parse:function(y,w,s,x){if(y===g){this._rgba=[null,null,null,null];
return this
}if(y.jquery||y.nodeType){y=r(y).css(w);
w=g}var v=this,u=r.type(y),t=this._rgba=[];
if(w!==g){y=[y,w,s,x];
u="array"}if(u==="string"){return this.parse(i(y)||d._default)
}if(u==="array"){o(m.rgba.props,function(z,A){t[A.idx]=l(y[A.idx],A)
});return this
}if(u==="object"){if(y instanceof h){o(m,function(z,A){if(y[A.cache]){v[A.cache]=y[A.cache].slice()
}})}else{o(m,function(A,B){var z=B.cache;
o(B.props,function(C,D){if(!v[z]&&B.to){if(C==="alpha"||y[C]==null){return
}v[z]=B.to(v._rgba)
}v[z][D.idx]=l(y[C],D,true)
});if(v[z]&&r.inArray(null,v[z].slice(0,3))<0){v[z][3]=1;
if(B.from){v._rgba=B.from(v[z])
}}})}return this
}},is:function(u){var s=h(u),v=true,t=this;
o(m,function(w,y){var z,x=s[y.cache];
if(x){z=t[y.cache]||y.to&&y.to(t._rgba)||[];
o(y.props,function(A,B){if(x[B.idx]!=null){v=(x[B.idx]===z[B.idx]);
return v}})
}return v});
return v},_space:function(){var s=[],t=this;
o(m,function(u,v){if(t[v.cache]){s.push(u)
}});return s.pop()
},transition:function(t,z){var u=h(t),v=u._space(),w=m[v],x=this.alpha()===0?h("transparent"):this,y=x[w.cache]||w.to(x._rgba),s=y.slice();
u=u[w.cache];
o(w.props,function(D,F){var C=F.idx,B=y[C],A=u[C],E=q[F.type]||{};
if(A===null){return
}if(B===null){s[C]=A
}else{if(E.mod){if(A-B>E.mod/2){B+=E.mod
}else{if(B-A>E.mod/2){B-=E.mod
}}}s[C]=l((A-B)*z+B,F)
}});return this[v](s)
},blend:function(v){if(this._rgba[3]===1){return this
}var u=this._rgba.slice(),t=u.pop(),s=h(v)._rgba;
return h(r.map(u,function(w,x){return(1-t)*s[x]+t*w
}))},toRgbaString:function(){var t="rgba(",s=r.map(this._rgba,function(u,w){return u==null?(w>2?1:0):u
});if(s[3]===1){s.pop();
t="rgb("}return t+s.join()+")"
},toHslaString:function(){var t="hsla(",s=r.map(this.hsla(),function(u,w){if(u==null){u=w>2?1:0
}if(w&&w<3){u=Math.round(u*100)+"%"
}return u});
if(s[3]===1){s.pop();
t="hsl("}return t+s.join()+")"
},toHexString:function(s){var t=this._rgba.slice(),u=t.pop();
if(s){t.push(~~(u*255))
}return"#"+r.map(t,function(w){w=(w||0).toString(16);
return w.length===1?"0"+w:w
}).join("")
},toString:function(){return this._rgba[3]===0?"transparent":this.toRgbaString()
}});h.fn.parse.prototype=h.fn;
function f(u,t,s){s=(s+1)%1;
if(s*6<1){return u+(t-u)*s*6
}if(s*2<1){return t
}if(s*3<2){return u+(t-u)*((2/3)-s)*6
}return u}m.hsla.to=function(v){if(v[0]==null||v[1]==null||v[2]==null){return[null,null,null,v[3]]
}var t=v[0]/255,y=v[1]/255,z=v[2]/255,B=v[3],A=Math.max(t,y,z),w=Math.min(t,y,z),C=A-w,D=A+w,u=D*0.5,x,E;
if(w===A){x=0
}else{if(t===A){x=(60*(y-z)/C)+360
}else{if(y===A){x=(60*(z-t)/C)+120
}else{x=(60*(t-y)/C)+240
}}}if(C===0){E=0
}else{if(u<=0.5){E=C/D
}else{E=C/(2-D)
}}return[Math.round(x)%360,E,u,B==null?1:B]
};m.hsla.from=function(x){if(x[0]==null||x[1]==null||x[2]==null){return[null,null,null,x[3]]
}var w=x[0]/360,v=x[1],u=x[2],t=x[3],y=u<=0.5?u*(1+v):u+v-u*v,z=2*u-y;
return[Math.round(f(z,y,w+(1/3))*255),Math.round(f(z,y,w)*255),Math.round(f(z,y,w-(1/3))*255),t]
};o(m,function(t,v){var u=v.props,s=v.cache,x=v.to,w=v.from;
h.fn[t]=function(C){if(x&&!this[s]){this[s]=x(this._rgba)
}if(C===g){return this[s].slice()
}var z,B=r.type(C),y=(B==="array"||B==="object")?C:arguments,A=this[s].slice();
o(u,function(D,F){var E=y[B==="object"?D:F.idx];
if(E==null){E=A[F.idx]
}A[F.idx]=l(E,F)
});if(w){z=h(w(A));
z[s]=A;return z
}else{return h(A)
}};o(u,function(y,z){if(h.fn[y]){return
}h.fn[y]=function(D){var F=r.type(D),C=(y==="alpha"?(this._hsla?"hsla":"rgba"):t),B=this[C](),E=B[z.idx],A;
if(F==="undefined"){return E
}if(F==="function"){D=D.call(this,E);
F=r.type(D)
}if(D==null&&z.empty){return this
}if(F==="string"){A=k.exec(D);
if(A){D=E+parseFloat(A[2])*(A[1]==="+"?1:-1)
}}B[z.idx]=D;
return this[C](B)
}})});h.hook=function(t){var s=t.split(" ");
o(s,function(u,v){r.cssHooks[v]={set:function(z,A){var x,y,w="";
if(A!=="transparent"&&(r.type(A)!=="string"||(x=i(A)))){A=h(x||A);
if(!p.rgba&&A._rgba[3]!==1){y=v==="backgroundColor"?z.parentNode:z;
while((w===""||w==="transparent")&&y&&y.style){try{w=r.css(y,"backgroundColor");
y=y.parentNode
}catch(B){}}A=A.blend(w&&w!=="transparent"?w:"_default")
}A=A.toRgbaString()
}try{z.style[v]=A
}catch(B){}}};
r.fx.step[v]=function(w){if(!w.colorInit){w.start=h(w.elem,v);
w.end=h(w.end);
w.colorInit=true
}r.cssHooks[v].set(w.elem,w.start.transition(w.end,w.pos))
}})};h.hook(n);
r.cssHooks.borderColor={expand:function(t){var s={};
o(["Top","Right","Bottom","Left"],function(v,u){s["border"+u+"Color"]=t
});return s
}};d=r.Color.names={aqua:"#00ffff",black:"#000000",blue:"#0000ff",fuchsia:"#ff00ff",gray:"#808080",green:"#008000",lime:"#00ff00",maroon:"#800000",navy:"#000080",olive:"#808000",purple:"#800080",red:"#ff0000",silver:"#c0c0c0",teal:"#008080",white:"#ffffff",yellow:"#ffff00",transparent:[null,null,null,0],_default:"#ffffff"}
})(jQuery);
(function(){var e=["add","remove","toggle"],f={border:1,borderBottom:1,borderColor:1,borderLeft:1,borderRight:1,borderTop:1,borderWidth:1,margin:1,padding:1};
a.each(["borderLeftStyle","borderRightStyle","borderBottomStyle","borderTopStyle"],function(h,i){a.fx.step[i]=function(j){if(j.end!=="none"&&!j.setAttr||j.pos===1&&!j.setAttr){jQuery.style(j.elem,i,j.end);
j.setAttr=true
}}});function g(l){var i,h,j=l.ownerDocument.defaultView?l.ownerDocument.defaultView.getComputedStyle(l,null):l.currentStyle,k={};
if(j&&j.length&&j[0]&&j[j[0]]){h=j.length;
while(h--){i=j[h];
if(typeof j[i]==="string"){k[a.camelCase(i)]=j[i]
}}}else{for(i in j){if(typeof j[i]==="string"){k[i]=j[i]
}}}return k
}function d(h,j){var l={},i,k;
for(i in j){k=j[i];
if(h[i]!==k){if(!f[i]){if(a.fx.step[i]||!isNaN(parseFloat(k))){l[i]=k
}}}}return l
}if(!a.fn.addBack){a.fn.addBack=function(h){return this.add(h==null?this.prevObject:this.prevObject.filter(h))
}}a.effects.animateClass=function(h,i,l,k){var j=a.speed(i,l,k);
return this.queue(function(){var o=a(this),m=o.attr("class")||"",n,p=j.children?o.find("*").addBack():o;
p=p.map(function(){var q=a(this);
return{el:q,start:g(this)}
});n=function(){a.each(e,function(q,r){if(h[r]){o[r+"Class"](h[r])
}})};n();p=p.map(function(){this.end=g(this.el[0]);
this.diff=d(this.start,this.end);
return this
});o.attr("class",m);
p=p.map(function(){var s=this,q=a.Deferred(),r=a.extend({},j,{queue:false,complete:function(){q.resolve(s)
}});this.el.animate(this.diff,r);
return q.promise()
});a.when.apply(a,p.get()).done(function(){n();
a.each(arguments,function(){var q=this.el;
a.each(this.diff,function(r){q.css(r,"")
})});j.complete.call(o[0])
})})};a.fn.extend({addClass:(function(h){return function(j,i,l,k){return i?a.effects.animateClass.call(this,{add:j},i,l,k):h.apply(this,arguments)
}})(a.fn.addClass),removeClass:(function(h){return function(j,i,l,k){return arguments.length>1?a.effects.animateClass.call(this,{remove:j},i,l,k):h.apply(this,arguments)
}})(a.fn.removeClass),toggleClass:(function(h){return function(k,j,i,m,l){if(typeof j==="boolean"||j===c){if(!i){return h.apply(this,arguments)
}else{return a.effects.animateClass.call(this,(j?{add:k}:{remove:k}),i,m,l)
}}else{return a.effects.animateClass.call(this,{toggle:k},j,i,m)
}}})(a.fn.toggleClass),switchClass:function(h,j,i,l,k){return a.effects.animateClass.call(this,{add:j,remove:h},i,l,k)
}})})();(function(){a.extend(a.effects,{version:"1.10.3",save:function(g,h){for(var f=0;
f<h.length;
f++){if(h[f]!==null){g.data(b+h[f],g[0].style[h[f]])
}}},restore:function(g,j){var h,f;
for(f=0;f<j.length;
f++){if(j[f]!==null){h=g.data(b+j[f]);
if(h===c){h=""
}g.css(j[f],h)
}}},setMode:function(f,g){if(g==="toggle"){g=f.is(":hidden")?"show":"hide"
}return g},getBaseline:function(g,h){var i,f;
switch(g[0]){case"top":i=0;
break;case"middle":i=0.5;
break;case"bottom":i=1;
break;default:i=g[0]/h.height
}switch(g[1]){case"left":f=0;
break;case"center":f=0.5;
break;case"right":f=1;
break;default:f=g[1]/h.width
}return{x:f,y:i}
},createWrapper:function(g){if(g.parent().is(".ui-effects-wrapper")){return g.parent()
}var h={width:g.outerWidth(true),height:g.outerHeight(true),"float":g.css("float")},k=a("<div></div>").addClass("ui-effects-wrapper").css({fontSize:"100%",background:"transparent",border:"none",margin:0,padding:0}),f={width:g.width(),height:g.height()},j=document.activeElement;
try{j.id}catch(i){j=document.body
}g.wrap(k);
if(g[0]===j||a.contains(g[0],j)){a(j).focus()
}k=g.parent();
if(g.css("position")==="static"){k.css({position:"relative"});
g.css({position:"relative"})
}else{a.extend(h,{position:g.css("position"),zIndex:g.css("z-index")});
a.each(["top","left","bottom","right"],function(l,m){h[m]=g.css(m);
if(isNaN(parseInt(h[m],10))){h[m]="auto"
}});g.css({position:"relative",top:0,left:0,right:"auto",bottom:"auto"})
}g.css(f);return k.css(h).show()
},removeWrapper:function(f){var g=document.activeElement;
if(f.parent().is(".ui-effects-wrapper")){f.parent().replaceWith(f);
if(f[0]===g||a.contains(f[0],g)){a(g).focus()
}}return f},setTransition:function(g,i,f,h){h=h||{};
a.each(i,function(k,j){var l=g.cssUnit(j);
if(l[0]>0){h[j]=l[0]*f+l[1]
}});return h
}});function d(g,f,h,i){if(a.isPlainObject(g)){f=g;
g=g.effect}g={effect:g};
if(f==null){f={}
}if(a.isFunction(f)){i=f;
h=null;f={}
}if(typeof f==="number"||a.fx.speeds[f]){i=h;
h=f;f={}}if(a.isFunction(h)){i=h;
h=null}if(f){a.extend(g,f)
}h=h||f.duration;
g.duration=a.fx.off?0:typeof h==="number"?h:h in a.fx.speeds?a.fx.speeds[h]:a.fx.speeds._default;
g.complete=i||f.complete;
return g}function e(f){if(!f||typeof f==="number"||a.fx.speeds[f]){return true
}if(typeof f==="string"&&!a.effects.effect[f]){return true
}if(a.isFunction(f)){return true
}if(typeof f==="object"&&!f.effect){return true
}return false
}a.fn.extend({effect:function(){var h=d.apply(this,arguments),j=h.mode,f=h.queue,g=a.effects.effect[h.effect];
if(a.fx.off||!g){if(j){return this[j](h.duration,h.complete)
}else{return this.each(function(){if(h.complete){h.complete.call(this)
}})}}function i(m){var n=a(this),l=h.complete,o=h.mode;
function k(){if(a.isFunction(l)){l.call(n[0])
}if(a.isFunction(m)){m()
}}if(n.is(":hidden")?o==="hide":o==="show"){n[o]();
k()}else{g.call(n[0],h,k)
}}return f===false?this.each(i):this.queue(f||"fx",i)
},show:(function(f){return function(h){if(e(h)){return f.apply(this,arguments)
}else{var g=d.apply(this,arguments);
g.mode="show";
return this.effect.call(this,g)
}}})(a.fn.show),hide:(function(f){return function(h){if(e(h)){return f.apply(this,arguments)
}else{var g=d.apply(this,arguments);
g.mode="hide";
return this.effect.call(this,g)
}}})(a.fn.hide),toggle:(function(f){return function(h){if(e(h)||typeof h==="boolean"){return f.apply(this,arguments)
}else{var g=d.apply(this,arguments);
g.mode="toggle";
return this.effect.call(this,g)
}}})(a.fn.toggle),cssUnit:function(f){var g=this.css(f),h=[];
a.each(["em","px","%","pt"],function(j,k){if(g.indexOf(k)>0){h=[parseFloat(g),k]
}});return h
}})})();(function(){var d={};
a.each(["Quad","Cubic","Quart","Quint","Expo"],function(f,e){d[e]=function(g){return Math.pow(g,f+2)
}});a.extend(d,{Sine:function(e){return 1-Math.cos(e*Math.PI/2)
},Circ:function(e){return 1-Math.sqrt(1-e*e)
},Elastic:function(e){return e===0||e===1?e:-Math.pow(2,8*(e-1))*Math.sin(((e-1)*80-7.5)*Math.PI/15)
},Back:function(e){return e*e*(3*e-2)
},Bounce:function(g){var e,f=4;
while(g<((e=Math.pow(2,--f))-1)/11){}return 1/Math.pow(4,3-f)-7.5625*Math.pow((e*3-2)/22-g,2)
}});a.each(d,function(f,e){a.easing["easeIn"+f]=e;
a.easing["easeOut"+f]=function(g){return 1-e(1-g)
};a.easing["easeInOut"+f]=function(g){return g<0.5?e(g*2)/2:1-e(g*-2+2)/2
}})})()})(jQuery);
(function(b,d){var a=/up|down|vertical/,c=/up|left|vertical|horizontal/;
b.effects.effect.blind=function(g,m){var h=b(this),q=["position","top","bottom","left","right","height","width"],n=b.effects.setMode(h,g.mode||"hide"),r=g.direction||"up",j=a.test(r),i=j?"height":"width",p=j?"top":"left",t=c.test(r),l={},s=n==="show",f,e,k;
if(h.parent().is(".ui-effects-wrapper")){b.effects.save(h.parent(),q)
}else{b.effects.save(h,q)
}h.show();f=b.effects.createWrapper(h).css({overflow:"hidden"});
e=f[i]();k=parseFloat(f.css(p))||0;
l[i]=s?e:0;
if(!t){h.css(j?"bottom":"right",0).css(j?"top":"left","auto").css({position:"absolute"});
l[p]=s?k:e+k
}if(s){f.css(i,0);
if(!t){f.css(p,k+e)
}}f.animate(l,{duration:g.duration,easing:g.easing,queue:false,complete:function(){if(n==="hide"){h.hide()
}b.effects.restore(h,q);
b.effects.removeWrapper(h);
m()}})}})(jQuery);
(function(a,b){a.effects.effect.bounce=function(m,l){var c=a(this),d=["position","top","bottom","left","right","height","width"],k=a.effects.setMode(c,m.mode||"effect"),j=k==="hide",v=k==="show",w=m.direction||"up",e=m.distance,h=m.times||5,x=h*2+(v||j?1:0),u=m.duration/x,p=m.easing,f=(w==="up"||w==="down")?"top":"left",n=(w==="up"||w==="left"),t,g,s,q=c.queue(),r=q.length;
if(v||j){d.push("opacity")
}a.effects.save(c,d);
c.show();a.effects.createWrapper(c);
if(!e){e=c[f==="top"?"outerHeight":"outerWidth"]()/3
}if(v){s={opacity:1};
s[f]=0;c.css("opacity",0).css(f,n?-e*2:e*2).animate(s,u,p)
}if(j){e=e/Math.pow(2,h-1)
}s={};s[f]=0;
for(t=0;t<h;
t++){g={};g[f]=(n?"-=":"+=")+e;
c.animate(g,u,p).animate(s,u,p);
e=j?e*2:e/2
}if(j){g={opacity:0};
g[f]=(n?"-=":"+=")+e;
c.animate(g,u,p)
}c.queue(function(){if(j){c.hide()
}a.effects.restore(c,d);
a.effects.removeWrapper(c);
l()});if(r>1){q.splice.apply(q,[1,0].concat(q.splice(r,x+1)))
}c.dequeue()
}})(jQuery);
(function(a,b){a.effects.effect.clip=function(f,i){var g=a(this),m=["position","top","bottom","left","right","height","width"],l=a.effects.setMode(g,f.mode||"hide"),p=l==="show",n=f.direction||"vertical",k=n==="vertical",q=k?"height":"width",j=k?"top":"left",h={},d,e,c;
a.effects.save(g,m);
g.show();d=a.effects.createWrapper(g).css({overflow:"hidden"});
e=(g[0].tagName==="IMG")?d:g;
c=e[q]();if(p){e.css(q,0);
e.css(j,c/2)
}h[q]=p?c:0;
h[j]=p?0:c/2;
e.animate(h,{queue:false,duration:f.duration,easing:f.easing,complete:function(){if(!p){g.hide()
}a.effects.restore(g,m);
a.effects.removeWrapper(g);
i()}})}})(jQuery);
(function(a,b){a.effects.effect.drop=function(d,h){var e=a(this),j=["position","top","bottom","left","right","opacity","height","width"],i=a.effects.setMode(e,d.mode||"hide"),l=i==="show",k=d.direction||"left",f=(k==="up"||k==="down")?"top":"left",m=(k==="up"||k==="left")?"pos":"neg",g={opacity:l?1:0},c;
a.effects.save(e,j);
e.show();a.effects.createWrapper(e);
c=d.distance||e[f==="top"?"outerHeight":"outerWidth"](true)/2;
if(l){e.css("opacity",0).css(f,m==="pos"?-c:c)
}g[f]=(l?(m==="pos"?"+=":"-="):(m==="pos"?"-=":"+="))+c;
e.animate(g,{queue:false,duration:d.duration,easing:d.easing,complete:function(){if(i==="hide"){e.hide()
}a.effects.restore(e,j);
a.effects.removeWrapper(e);
h()}})}})(jQuery);
(function(a,b){a.effects.effect.explode=function(s,r){var k=s.pieces?Math.round(Math.sqrt(s.pieces)):3,d=k,c=a(this),m=a.effects.setMode(c,s.mode||"hide"),w=m==="show",g=c.show().css("visibility","hidden").offset(),t=Math.ceil(c.outerWidth()/d),q=Math.ceil(c.outerHeight()/k),h=[],v,u,e,p,n,l;
function x(){h.push(this);
if(h.length===k*d){f()
}}for(v=0;v<k;
v++){p=g.top+v*q;
l=v-(k-1)/2;
for(u=0;u<d;
u++){e=g.left+u*t;
n=u-(d-1)/2;
c.clone().appendTo("body").wrap("<div></div>").css({position:"absolute",visibility:"visible",left:-u*t,top:-v*q}).parent().addClass("ui-effects-explode").css({position:"absolute",overflow:"hidden",width:t,height:q,left:e+(w?n*t:0),top:p+(w?l*q:0),opacity:w?0:1}).animate({left:e+(w?0:n*t),top:p+(w?0:l*q),opacity:w?1:0},s.duration||500,s.easing,x)
}}function f(){c.css({visibility:"visible"});
a(h).remove();
if(!w){c.hide()
}r()}}})(jQuery);
(function(a,b){a.effects.effect.fade=function(f,c){var d=a(this),e=a.effects.setMode(d,f.mode||"toggle");
d.animate({opacity:e},{queue:false,duration:f.duration,easing:f.easing,complete:c})
}})(jQuery);
(function(a,b){a.effects.effect.fold=function(e,i){var f=a(this),n=["position","top","bottom","left","right","height","width"],k=a.effects.setMode(f,e.mode||"hide"),r=k==="show",l=k==="hide",t=e.size||15,m=/([0-9]+)%/.exec(t),s=!!e.horizFirst,j=r!==s,g=j?["width","height"]:["height","width"],h=e.duration/2,d,c,q={},p={};
a.effects.save(f,n);
f.show();d=a.effects.createWrapper(f).css({overflow:"hidden"});
c=j?[d.width(),d.height()]:[d.height(),d.width()];
if(m){t=parseInt(m[1],10)/100*c[l?0:1]
}if(r){d.css(s?{height:0,width:t}:{height:t,width:0})
}q[g[0]]=r?c[0]:t;
p[g[1]]=r?c[1]:0;
d.animate(q,h,e.easing).animate(p,h,e.easing,function(){if(l){f.hide()
}a.effects.restore(f,n);
a.effects.removeWrapper(f);
i()})}})(jQuery);
(function(a,b){a.effects.effect.highlight=function(h,c){var e=a(this),d=["backgroundImage","backgroundColor","opacity"],g=a.effects.setMode(e,h.mode||"show"),f={backgroundColor:e.css("backgroundColor")};
if(g==="hide"){f.opacity=0
}a.effects.save(e,d);
e.show().css({backgroundImage:"none",backgroundColor:h.color||"#ffff99"}).animate(f,{queue:false,duration:h.duration,easing:h.easing,complete:function(){if(g==="hide"){e.hide()
}a.effects.restore(e,d);
c()}})}})(jQuery);
(function(a,b){a.effects.effect.pulsate=function(c,g){var e=a(this),k=a.effects.setMode(e,c.mode||"show"),p=k==="show",l=k==="hide",q=(p||k==="hide"),m=((c.times||5)*2)+(q?1:0),f=c.duration/m,n=0,j=e.queue(),d=j.length,h;
if(p||!e.is(":visible")){e.css("opacity",0).show();
n=1}for(h=1;
h<m;h++){e.animate({opacity:n},f,c.easing);
n=1-n}e.animate({opacity:n},f,c.easing);
e.queue(function(){if(l){e.hide()
}g()});if(d>1){j.splice.apply(j,[1,0].concat(j.splice(d,m+1)))
}e.dequeue()
}})(jQuery);
(function(a,b){a.effects.effect.puff=function(j,c){var h=a(this),i=a.effects.setMode(h,j.mode||"hide"),f=i==="hide",g=parseInt(j.percent,10)||150,e=g/100,d={height:h.height(),width:h.width(),outerHeight:h.outerHeight(),outerWidth:h.outerWidth()};
a.extend(j,{effect:"scale",queue:false,fade:true,mode:i,complete:c,percent:f?g:100,from:f?d:{height:d.height*e,width:d.width*e,outerHeight:d.outerHeight*e,outerWidth:d.outerWidth*e}});
h.effect(j)
};a.effects.effect.scale=function(c,f){var d=a(this),l=a.extend(true,{},c),g=a.effects.setMode(d,c.mode||"effect"),h=parseInt(c.percent,10)||(parseInt(c.percent,10)===0?0:(g==="hide"?0:100)),j=c.direction||"both",k=c.origin,e={height:d.height(),width:d.width(),outerHeight:d.outerHeight(),outerWidth:d.outerWidth()},i={y:j!=="horizontal"?(h/100):1,x:j!=="vertical"?(h/100):1};
l.effect="size";
l.queue=false;
l.complete=f;
if(g!=="effect"){l.origin=k||["middle","center"];
l.restore=true
}l.from=c.from||(g==="show"?{height:0,width:0,outerHeight:0,outerWidth:0}:e);
l.to={height:e.height*i.y,width:e.width*i.x,outerHeight:e.outerHeight*i.y,outerWidth:e.outerWidth*i.x};
if(l.fade){if(g==="show"){l.from.opacity=0;
l.to.opacity=1
}if(g==="hide"){l.from.opacity=1;
l.to.opacity=0
}}d.effect(l)
};a.effects.effect.size=function(l,k){var q,i,j,c=a(this),p=["position","top","bottom","left","right","width","height","overflow","opacity"],n=["position","top","bottom","left","right","overflow","opacity"],m=["width","height","overflow"],g=["fontSize"],s=["borderTopWidth","borderBottomWidth","paddingTop","paddingBottom"],d=["borderLeftWidth","borderRightWidth","paddingLeft","paddingRight"],h=a.effects.setMode(c,l.mode||"effect"),r=l.restore||h!=="effect",v=l.scale||"both",t=l.origin||["middle","center"],u=c.css("position"),e=r?p:n,f={height:0,width:0,outerHeight:0,outerWidth:0};
if(h==="show"){c.show()
}q={height:c.height(),width:c.width(),outerHeight:c.outerHeight(),outerWidth:c.outerWidth()};
if(l.mode==="toggle"&&h==="show"){c.from=l.to||f;
c.to=l.from||q
}else{c.from=l.from||(h==="show"?f:q);
c.to=l.to||(h==="hide"?f:q)
}j={from:{y:c.from.height/q.height,x:c.from.width/q.width},to:{y:c.to.height/q.height,x:c.to.width/q.width}};
if(v==="box"||v==="both"){if(j.from.y!==j.to.y){e=e.concat(s);
c.from=a.effects.setTransition(c,s,j.from.y,c.from);
c.to=a.effects.setTransition(c,s,j.to.y,c.to)
}if(j.from.x!==j.to.x){e=e.concat(d);
c.from=a.effects.setTransition(c,d,j.from.x,c.from);
c.to=a.effects.setTransition(c,d,j.to.x,c.to)
}}if(v==="content"||v==="both"){if(j.from.y!==j.to.y){e=e.concat(g).concat(m);
c.from=a.effects.setTransition(c,g,j.from.y,c.from);
c.to=a.effects.setTransition(c,g,j.to.y,c.to)
}}a.effects.save(c,e);
c.show();a.effects.createWrapper(c);
c.css("overflow","hidden").css(c.from);
if(t){i=a.effects.getBaseline(t,q);
c.from.top=(q.outerHeight-c.outerHeight())*i.y;
c.from.left=(q.outerWidth-c.outerWidth())*i.x;
c.to.top=(q.outerHeight-c.to.outerHeight)*i.y;
c.to.left=(q.outerWidth-c.to.outerWidth)*i.x
}c.css(c.from);
if(v==="content"||v==="both"){s=s.concat(["marginTop","marginBottom"]).concat(g);
d=d.concat(["marginLeft","marginRight"]);
m=p.concat(s).concat(d);
c.find("*[width]").each(function(){var w=a(this),o={height:w.height(),width:w.width(),outerHeight:w.outerHeight(),outerWidth:w.outerWidth()};
if(r){a.effects.save(w,m)
}w.from={height:o.height*j.from.y,width:o.width*j.from.x,outerHeight:o.outerHeight*j.from.y,outerWidth:o.outerWidth*j.from.x};
w.to={height:o.height*j.to.y,width:o.width*j.to.x,outerHeight:o.height*j.to.y,outerWidth:o.width*j.to.x};
if(j.from.y!==j.to.y){w.from=a.effects.setTransition(w,s,j.from.y,w.from);
w.to=a.effects.setTransition(w,s,j.to.y,w.to)
}if(j.from.x!==j.to.x){w.from=a.effects.setTransition(w,d,j.from.x,w.from);
w.to=a.effects.setTransition(w,d,j.to.x,w.to)
}w.css(w.from);
w.animate(w.to,l.duration,l.easing,function(){if(r){a.effects.restore(w,m)
}})})}c.animate(c.to,{queue:false,duration:l.duration,easing:l.easing,complete:function(){if(c.to.opacity===0){c.css("opacity",c.from.opacity)
}if(h==="hide"){c.hide()
}a.effects.restore(c,e);
if(!r){if(u==="static"){c.css({position:"relative",top:c.to.top,left:c.to.left})
}else{a.each(["top","left"],function(o,w){c.css(w,function(y,A){var z=parseInt(A,10),x=o?c.to.left:c.to.top;
if(A==="auto"){return x+"px"
}return z+x+"px"
})})}}a.effects.removeWrapper(c);
k()}})}})(jQuery);
(function(a,b){a.effects.effect.shake=function(l,k){var c=a(this),d=["position","top","bottom","left","right","height","width"],j=a.effects.setMode(c,l.mode||"effect"),u=l.direction||"left",e=l.distance||20,h=l.times||3,v=h*2+1,q=Math.round(l.duration/v),g=(u==="up"||u==="down")?"top":"left",f=(u==="up"||u==="left"),t={},s={},r={},p,m=c.queue(),n=m.length;
a.effects.save(c,d);
c.show();a.effects.createWrapper(c);
t[g]=(f?"-=":"+=")+e;
s[g]=(f?"+=":"-=")+e*2;
r[g]=(f?"-=":"+=")+e*2;
c.animate(t,q,l.easing);
for(p=1;p<h;
p++){c.animate(s,q,l.easing).animate(r,q,l.easing)
}c.animate(s,q,l.easing).animate(t,q/2,l.easing).queue(function(){if(j==="hide"){c.hide()
}a.effects.restore(c,d);
a.effects.removeWrapper(c);
k()});if(n>1){m.splice.apply(m,[1,0].concat(m.splice(n,v+1)))
}c.dequeue()
}})(jQuery);
(function(a,b){a.effects.effect.slide=function(e,i){var f=a(this),k=["position","top","bottom","left","right","width","height"],j=a.effects.setMode(f,e.mode||"show"),m=j==="show",l=e.direction||"left",g=(l==="up"||l==="down")?"top":"left",d=(l==="up"||l==="left"),c,h={};
a.effects.save(f,k);
f.show();c=e.distance||f[g==="top"?"outerHeight":"outerWidth"](true);
a.effects.createWrapper(f).css({overflow:"hidden"});
if(m){f.css(g,d?(isNaN(c)?"-"+c:-c):c)
}h[g]=(m?(d?"+=":"-="):(d?"-=":"+="))+c;
f.animate(h,{queue:false,duration:e.duration,easing:e.easing,complete:function(){if(j==="hide"){f.hide()
}a.effects.restore(f,k);
a.effects.removeWrapper(f);
i()}})}})(jQuery);
(function(a,b){a.effects.effect.transfer=function(d,h){var f=a(this),k=a(d.to),n=k.css("position")==="fixed",j=a("body"),l=n?j.scrollTop():0,m=n?j.scrollLeft():0,c=k.offset(),g={top:c.top-l,left:c.left-m,height:k.innerHeight(),width:k.innerWidth()},i=f.offset(),e=a("<div class='ui-effects-transfer'></div>").appendTo(document.body).addClass(d.className).css({top:i.top-l,left:i.left-m,height:f.innerHeight(),width:f.innerWidth(),position:n?"fixed":"absolute"}).animate(g,d.duration,d.easing,function(){e.remove();
h()})}})(jQuery);