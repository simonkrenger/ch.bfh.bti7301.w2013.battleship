/*!CK:2448538433!*//*1379308038,178142805*/

if (self.CavalryLogger) { CavalryLogger.start_js(["+UtGp"]); }

__d("HTML",["Bootloader","createNodesFromMarkup","emptyFunction","evalGlobal","invariant"],function(a,b,c,d,e,f){var g=b('Bootloader'),h=b('createNodesFromMarkup'),i=b('emptyFunction'),j=b('evalGlobal'),k=b('invariant'),l=/(<(\w+)[^>]*?)\/>/g,m={abbr:true,area:true,br:true,col:true,embed:true,hr:true,img:true,input:true,link:true,meta:true,param:true};function n(o){if(o&&typeof o.__html==='string')o=o.__html;if(!(this instanceof n)){if(o instanceof n)return o;return new n(o);}if(o){var p=typeof o;k(p==='string');}this._markup=o||'';this._defer=false;this._extraAction='';this._nodes=null;this._inlineJS=i;this._rootNode=null;}n.prototype.toString=function(){var o=this._markup;if(this._extraAction)o+='<script type="text/javascript">'+this._extraAction+'</scr'+'ipt>';return o;};n.prototype.getContent=function(){return this._markup;};n.prototype.getNodes=function(){this._fillCache();return this._nodes;};n.prototype.getRootNode=function(){k(!this._rootNode);var o=this.getNodes();if(o.length===1){this._rootNode=o[0];}else{var p=document.createDocumentFragment();for(var q=0;q<o.length;q++)p.appendChild(o[q]);this._rootNode=p;}return this._rootNode;};n.prototype.getAction=function(){this._fillCache();var o=function(){this._inlineJS();j(this._extraAction);}.bind(this);return this._defer?function(){setTimeout(o,0);}:o;};n.prototype._fillCache=function(){if(this._nodes!==null)return;if(!this._markup){this._nodes=[];return;}var o=this._markup.replace(l,function(r,s,t){return m[t.toLowerCase()]?r:s+'></'+t+'>';}),p=null,q=h(o,function(r){p=p||[];p.push(r.src?g.requestJSResource.bind(g,r.src):j.bind(null,r.innerHTML));r.parentNode.removeChild(r);});if(p)this._inlineJS=function(){for(var r=0;r<p.length;r++)p[r]();};this._nodes=q;};n.prototype.setAction=function(o){this._extraAction=o;return this;};n.prototype.setDeferred=function(o){this._defer=!!o;return this;};n.isHTML=function(o){return o&&(o instanceof n||o.__html!==undefined);};n.replaceJSONWrapper=function(o){return o&&o.__html!==undefined?new n(o.__html):o;};e.exports=n;});