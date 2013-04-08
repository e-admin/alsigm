// Title: Tigra Tree
// Description: See the demo at url
// URL: http://www.softcomplex.com/products/tigra_menu_tree/
// Version: 1.1
// Date: 11-12-2002 (mm-dd-yyyy)
// Notes: This script is free. Visit official site for further details.

function storeImgObject(imgSrc, icons, pos) {
	//alert(imgSrc);
	var o_icon = new Image();
	o_icon.src = imgSrc;
	icons[pos] = o_icon;
}

function loadTemplateImages(a_template, img_folder) {
	/*storeImgObject(a_template['icon_e'], a_template['imgs']);
	storeImgObject(a_template['icon_l'], a_template['imgs']);*/

	var o_icone = new Image(),
		o_iconl = new Image(),
		o_iconroot = new Image();
	o_icone.src = img_folder+a_template['icon_e'];
	o_iconl.src = img_folder+a_template['icon_l'];
	o_iconroot.src = img_folder+a_template['icon_root'];

	a_template['im_e'] = o_icone;
	a_template['im_l'] = o_iconl;
	a_template['im_root'] = o_iconroot;
	a_template['imgs_images'] = new Array();
	for (var img in a_template['images'])
		storeImgObject(img_folder+a_template['images'][img], a_template['imgs_images'], img);
	a_template['imgs_defaultIcons'] = new Array();
	for (var i = 0; i < a_template['defaultIcons'].length; i++)
		storeImgObject(img_folder+a_template['defaultIcons'][i], a_template['imgs_defaultIcons'], i);

	a_template['imgs_icons'] = new Array();
	for(var nodeType in a_template['icons']) {
		a_template['imgs_icons'][nodeType] = new Array();
		for (var i=0; i<a_template['icons'][nodeType].length; i++)
			storeImgObject(img_folder+a_template['icons'][nodeType][i], a_template['imgs_icons'][nodeType], i);
	}
	//alert(a_template['imgs_images']);
	//alert(a_template['imgs_defaultIcons']);
	//alert(a_template['imgs_icons']);
}

function none() {}

function show_props(obj, obj_name) {
   var result = "";
   for (var i in obj)
      result += obj_name + "." + i + " = " + obj[i] + "\n";
   return result
} 

function tree (a_items, a_template, img_folder, target) {
	this.img_path	= img_folder;
	//alert(img_folder);
	this.a_tpl      = a_template;
	this.a_config   = a_items;
	this.o_root     = this;
	this.a_index    = [];
	this.o_selected = null;
	this.path_selected_node = null;
	this.target = target;

	this.n_depth    = -1;
	this.openNodes = new Array();
	this.closedNodes = new Array();
	
	/*var o_icone = new Image(),
		o_iconl = new Image();
	o_icone.src = a_template['icon_e'];
	o_iconl.src = a_template['icon_l'];
	a_template['im_e'] = o_icone;
	a_template['im_l'] = o_iconl;
	for (var i = 0; i < 64; i++)
		if (a_template['icon_' + i]) {
			var o_icon = new Image();
			a_template['im_' + i] = o_icon;
			o_icon.src = a_template['icon_' + i];
		}*/
	loadTemplateImages(a_template, img_folder);
	//this.addOpenNode = function (node) {
	this.nodeOpened = function (node) {
		//alert(node.n_id);
		if (node.n_depth > 0)  {
			this.openNodes[node.n_id] = node.a_config[2];
			delete this.closedNodes[node.n_id];
		}
		//alert(show_props(this.openNodes, 'openNodes'));
	}
	//this.removeOpenNode = function (nodeToRemove) {
	this.nodeClosed = function (nodeClosed) {
		//this.openNodes.remove(this.openNodes.indexOf(nodeToRemove.a_config[2]));
		this.closedNodes[nodeClosed.n_id] = nodeClosed.a_config[2];
		delete this.openNodes[nodeClosed.n_id];
	}
	this.setSelectedNode = function (path_selected_node) {
		this.path_selected_node = path_selected_node;
		//alert('aaaaaaa '+this.path_selected_node);
	}

	this.toggle = function (n_id) {	
		var o_item = this.a_index[n_id]; 
		o_item.open(o_item.b_opened) ;
		//alert(o_item.b_opened) ;
		if (o_item.b_opened)
			this.nodeOpened(o_item);
		else
			this.nodeClosed(o_item);
		//alert(xScrollTop(null,window));
		setCookie('topPos', xScrollTop(null, window));

	};
	this.select = function (n_id) { return this.a_index[n_id].select(); };
	this.mout   = function (n_id) { this.a_index[n_id].upstatus(true) };
	this.mover  = function (n_id) { this.a_index[n_id].upstatus() };

	this.a_children = [];
	this.init = function() {
		for (var i = 0; i < a_items.length; i++)
			new tree_item(this, i);

		this.n_id = trees.length;
		trees[this.n_id] = this;
		
		for (var i = 0; i < this.a_children.length; i++) {
			var nodeCode = this.a_children[i].init();
			//alert(nodeCode);
			document.write(nodeCode);
			this.a_children[i].open(false,true);
		}
		var topPos = getCookie('topPos');
		if (topPos) {
			//alert('topPos cookie: '+topPos);
			removeCookie('topPos');
			var scrollTop = eval('parseInt(topPos, 10)');
			//if (scrollTop > 0)
			//alert(scrollTop);
			setTimeout('window.scroll(0, '+scrollTop+')',50);
			//	window.scroll(0, scrollTop);
		} else {
			if (this.o_selected) {
				var idSelectedNode = 'i_div' + this.n_id + '_' + this.o_selected.n_id;
				var selectedNodeObj = xGetElementById(idSelectedNode);
				if (selectedNodeObj && selectedNodeObj.parentElement) {
					var nodeY = xPageY(selectedNodeObj.parentElement);
					//alert(nodeY);
					//alert(xClientHeight());
					if (nodeY > xClientHeight())
						setTimeout('window.scroll(0, '+nodeY+')',50);
				}
			}
		}
	}

}
function tree_item (o_parent, n_order) {

	this.n_depth  = o_parent.n_depth + 1;
	this.a_config = o_parent.a_config[n_order + (this.n_depth ? 5 : 0)];
	if (!this.a_config) return;

	this.o_root    = o_parent.o_root;
	//alert(this.a_config[2]+' - '+this.o_root.path_selected_node);
	this.selected = (this.a_config[2] == this.o_root.path_selected_node);
	if (this.selected) {
		this.o_root.o_selected = this;
	}

	this.o_parent  = o_parent;
	this.n_order   = n_order;
	this.b_opened  = this.a_config[4];

	this.n_id = this.o_root.a_index.length;
	this.o_root.a_index[this.n_id] = this;
	o_parent.a_children[n_order] = this;

	this.a_children = [];
	for (var i = 0; i < this.a_config.length - 5; i++)
		new tree_item(this, i);

	this.get_icon = item_get_icon;
	this.generate_content= item_content;
	this.open     = item_open;
	this.select   = item_select;
	this.init     = item_init;
	this.upstatus = item_upstatus;
	this.is_last  = function () { return this.n_order == this.o_parent.a_children.length - 1 };
}

function item_content() {
	var o_idivID = 'i_div' + this.o_root.n_id + '_' + this.n_id;
	var o_idiv = get_element(o_idivID);
	//alert("item_open: "+('i_div' + this.o_root.n_id + '_' + this.n_id));
	if (o_idiv && this.a_config[3]) {	
		if (!o_idiv.innerHTML) {
			var a_children = [];
			for (var i = 0; i < this.a_children.length; i++) {
				a_children[i]= this.a_children[i].init();
				//alert(a_children[i]);
			}
			o_idiv.innerHTML = a_children.join('');
		}
		o_idiv.style.display = (this.b_opened ? 'block' : 'none');
		for (var i = 0; i < this.a_children.length; i++)
			if (this.a_children[i].b_opened) this.a_children[i].generate_content();

	}
}

function item_open (b_close, recursive) {
	//alert("item_open");
	//alert(this.a_children);
	//alert(this.a_config[3] && this.a_children.length == 0)
	if (this.a_config[3] && this.a_children.length == 0) {
		//alert(this.a_config[0]);

		// Mostrar fondo de espera
		//showWorkingDiv();

		// encapsular en un ServerSideInvoker
		var treeManagerForm = document.forms['treeManagerForm'];
		treeManagerForm.method.value = 'expandirNodo';
		treeManagerForm.node.value = this.a_config[2];
		//treeManagerForm.openNodes.value = this.o_root.openNodes.toString("-");
		treeManagerForm.openNodes.value = joinArray(this.o_root.openNodes, '-');
		treeManagerForm.closedNodes.value = joinArray(this.o_root.closedNodes, '-');
		if (this.o_root.o_selected)
			treeManagerForm.selectedNode.value = this.o_root.o_selected.a_config[2];

		treeManagerForm.target = self.name;
		treeManagerForm.submit();
		return;
		//window.location = "/archivo/action/manageVistaDeposito?method=expandirNodo&viewName=estructuraDeposito&node="+this.a_config[2];
	}
	/*
	var o_idivID = 'i_div' + this.o_root.n_id + '_' + this.n_id;
	var o_idiv = get_element(o_idivID);
	//alert("item_open: "+('i_div' + this.o_root.n_id + '_' + this.n_id));
	if (o_idiv) {	
		if (!o_idiv.innerHTML) {
			var a_children = [];
			for (var i = 0; i < this.a_children.length; i++) {
				a_children[i]= this.a_children[i].init();
				alert(a_children[i]);
			}
			o_idiv.innerHTML = a_children.join('');
		}
		o_idiv.style.display = (b_close ? 'none' : 'block');
	}
	*/
	this.b_opened = !b_close;
	this.generate_content();
	/*if (this.b_opened) {
		this.o_root.nodeOpened(this);
	} else {
		this.o_root.nodeClosed(this);
	}*/
	//alert('openNodes '+this.o_root.openNodes.toString("-"));

	//alert ('j_img' + this.o_root.n_id + '_' + this.n_id);
	var o_jicon = document.images['j_img' + this.o_root.n_id + '_' + this.n_id],
		o_iicon = document.images['i_img' + this.o_root.n_id + '_' + this.n_id];
	//alert(o_jicon.src);
	if (o_jicon) o_jicon.src = this.get_icon(true);
	if (o_iicon) o_iicon.src = this.get_icon();
	if (!b_close && recursive) {
		for (var i = 0; i < this.a_children.length; i++) {
			//alert(this.a_config[0]);
			if (this.a_children[i].b_opened)
				this.a_children[i].open(false,true);
		}
	}
	this.upstatus();
	//alert(this.o_root.openNodes);
}

function item_select (b_deselect) {
	this.selected = !b_deselect;
	//alert("item_select: "+this.selected);
	if (!b_deselect) {
		var o_olditem = this.o_root.o_selected;
		this.o_root.o_selected = this;
		if (o_olditem && (o_olditem.n_id != this.n_id))
			o_olditem.select(true);
	}
	var o_iicon = document.images['i_img' + this.o_root.n_id + '_' + this.n_id];
	if (o_iicon) o_iicon.src = this.get_icon();
	var itemLabel = get_element('i_txt' + this.o_root.n_id + '_' + this.n_id);
	if (itemLabel) {
		itemLabel.style.fontWeight = b_deselect ? 'bold' : 'normal';
		itemLabel.style.color = b_deselect ? '#363538' : 'D75454';
	}
	
	this.upstatus();
	return false;
	//return Boolean(this.a_config[2]);
}

function item_upstatus (b_clear) {
	window.setTimeout('window.status="' + (b_clear ? '' : this.a_config[0].replace('"','\\"') + (this.a_config[2] ? ' ('+ this.a_config[2] + ')' : '')) + '"', 10);
}

var clickWaiting = null;
function nodeClicked(tree_id, node_id) {
	var str = 'viewNodeContent('+tree_id+','+node_id+')';
	setTimeout(str,200);
	clickWaiting = str;
	//alert(clickWaiting);
}

function nodeDoubleClicked(tree_id, node_id) {
	//alert("doubleclick");
	if (clickWaiting != null) {
		clearTimeout(clickWaiting);
		//alert("clickcleared "+clickWaiting);
		clickWaiting = null;
	}
	trees[tree_id].toggle(node_id);
	viewNodeContent(tree_id, node_id);
	/*var str = 'trees[' + tree_id + '].toggle(' node_id ')
	var str = 'viewNodeContent('+tree_id+','+node_id+')';
	setTimeout(str,500);*/
}

function viewNodeContent(tree_id, node_id) {

	// Mostrar fondo de espera
	//showWorkingDiv();

	//alert("view node content");
	var tree = trees[tree_id];
	tree.select(node_id);
	var nodeViewForm = document.forms['viewNodeForm'];
	/*alert(xScrollTop(null,window));
	setCookie('topPos', xScrollTop(window));*/

	//nodeViewForm.method.value = 'verNodo';
	nodeViewForm.node.value = tree.a_index[node_id].a_config[2];
	//alert(tree.target+' - '+treeManagerForm.target);
	if (tree.target) {
		nodeViewForm.target = tree.target;
		//alert(nodeViewForm.target);
	} /*else
		treeManagerForm.target = self.name;*/

	nodeViewForm.submit();
/*
	alert("desp submit" + nodeViewForm.target);
	var oIframe = top.document.getElementById(tree.target);
alert("nombre" +	oIframe.name);
	oIframe.name= tree.target;
	alert("nombre" +	oIframe.name);
	alert(top.document.getElementById(tree.target));
	*/
}

function item_init () {
	//alert("item_init");
	var a_offset = [],
		o_current_item = this.o_parent;
	for (var i = this.n_depth; i > 1; i--) {
		a_offset[i] = '<img src="' + this.o_root.img_path + this.o_root.a_tpl[o_current_item.is_last() ? 'icon_e' : 'icon_l'] + '" border="0" align="absbottom">';
		o_current_item = o_current_item.o_parent;
	}
	//alert("item_init- "+a_offset.join(''));
	var item_label = '<img src="' + this.get_icon() + '" border="0" align="absbottom" name="i_img' + this.o_root.n_id + '_' + this.n_id + '" class="t' + this.o_root.n_id + 'im"> ' +this.a_config[0];
	if (this.a_config[2])
		item_label = '<a href="javascript:none()" onclick="viewNodeContent(' +this.o_root.n_id +','+this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')" class="t' + this.o_root.n_id + 'i" id="i_txt' + this.o_root.n_id + '_' + this.n_id + '" style="font-weight:'+(this.selected?'bold':'normal')+';color:'+(this.selected?'#D75454':'#363538')+'">'+item_label + '</a>';
	var result ='<table cellpadding="0" cellspacing="0" border="0"><tr><td nowrap>' + (this.n_depth ? a_offset.join('') + (this.a_config[3]
		? '<a href="javascript: trees[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')"><img src="' + this.get_icon(true) + '" border="0" align="absbottom" name="j_img' + this.o_root.n_id + '_' + this.n_id + '"></a>'
		: '<img src="' + this.get_icon(true) + '" border="0" align="absbottom">') : '') 
		+ item_label + '<div id="i_div' + this.o_root.n_id + '_' + this.n_id + '" style="display:none"></div>' + '</td></tr></table>';
		//+ '<a href="javascript:none()" onclick="viewNodeContent(' +this.o_root.n_id +','+this.n_id + ')" ondblclick="trees[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')" class="t' + this.o_root.n_id + 'i" id="i_txt' + this.o_root.n_id + '_' + this.n_id + '" style="font-weight:'+(this.selected?'normal':'bold')+';color:'+(this.selected?'#D75454':'#363538')+'"><img src="' + this.get_icon() + '" border="0" align="absbottom" name="i_img' + this.o_root.n_id + '_' + this.n_id + '" class="t' + this.o_root.n_id + 'im">'+this.a_config[0] + '</a></td></tr></table>' + (this.a_config[3] ? '<div id="i_div' + this.o_root.n_id + '_' + this.n_id + '" style="display:none"></div>' : '');
		//alert(result);
	return result;
}

function item_get_icon (b_junction) {
	var img_icon = null;
	if (b_junction) {
		//var junctionImages = this.o_root.a_tpl['images'];
		//alert("buscando images: "+((this.is_last()?3:0)+(this.a_config[3]?0:2)+(this.b_opened?1:0)));
		//alert("a: "+(this.o_root.a_tpl['imgs_images'][(this.is_last()?3:0)+(this.a_config[3]?0:2)+(this.b_opened?1:0)]).src);
		if (this.a_config[3]) {
			if (this.b_opened)
				img_icon = this.o_root.a_tpl['imgs_images'][this.is_last()?'lmenos':'menos'];
			else
				img_icon = this.o_root.a_tpl['imgs_images'][this.is_last()?'lmas':'mas'];
		} else
			img_icon = this.o_root.a_tpl['imgs_images'][this.is_last()?'lnodo':'nodo'];
		//img_icon = this.o_root.a_tpl['imgs_images'][(this.a_config[3]?2:0)+(this.b_opened?1:0)+(this.is_last()?1:0)];
	} else {
		var customIcon = null;
		if (window.getNodeImage)
			customIcon = getNodeImage(this);
//		if (this.o_root.a_tpl['imgs_icons'] && this.o_root.a_tpl['imgs_icons'][this.a_config[1]]) {
//			customIcon = this.o_root.a_tpl['imgs_icons'][this.a_config[1]][(this.a_config[3]?0:4)+(this.a_config[3]&&this.b_opened?2:0)+(this.selected?1:0)];
//		}
		img_icon = customIcon?customIcon:this.o_root.a_tpl['imgs_defaultIcons'][(this.a_config[3]?0:4)+(this.a_config[3]&&this.b_opened?2:0)+(this.selected == this?1:0)];
	}
//	alert(img_icon);
	return img_icon.src;
	//alert(this.o_root.a_tpl['images']);
	//return this.o_root.a_tpl['icon_' + ((this.n_depth ? 0 : 32) + (this.a_children.length ? 16 : 0) + (this.a_children.length && this.b_opened ? 8 : 0) + (!b_junction && this.o_root.o_selected == this ? 4 : 0) + (b_junction ? 2 : 0) + (b_junction && this.is_last() ? 1 : 0))];
	//return 'icons/empty.gif'
}

var trees = [];
get_element = document.all ?
	function (s_id) { return document.all[s_id] } :
	function (s_id) { return document.getElementById(s_id) };
