function storeImgObjectAmbitoDeposito(imgSrc, icons, pos) {
	var o_icon = new Image();
	o_icon.src = imgSrc;
	icons[pos] = o_icon;
}

function loadTemplateImagesAmbitoDeposito(a_template, img_folder) {
	var o_icone = new Image(),
		o_iconl = new Image();
	o_icone.src = img_folder+a_template['icon_e'];
	o_iconl.src = img_folder+a_template['icon_l'];

	a_template['im_e'] = o_icone;
	a_template['im_l'] = o_iconl;
	a_template['imgs_images'] = new Array();
	for (var img in a_template['images'])
		storeImgObjectAmbitoDeposito(img_folder+a_template['images'][img], a_template['imgs_images'], img);
	a_template['imgs_defaultIcons'] = new Array();
	for (var i = 0; i < a_template['defaultIcons'].length; i++)
		storeImgObjectAmbitoDeposito(img_folder+a_template['defaultIcons'][i], a_template['imgs_defaultIcons'], i);

	a_template['imgs_icons'] = new Array();
	for(var nodeType in a_template['icons']) {
		a_template['imgs_icons'][nodeType] = new Array();
		for (var i=0; i<a_template['icons'][nodeType].length; i++)
			storeImgObjectAmbitoDeposito(img_folder+a_template['icons'][nodeType][i], a_template['imgs_icons'][nodeType], i);
	}
}

function none() {}

function show_propsAmbitoDeposito(obj, obj_name) {
   var result = "";
   for (var i in obj)
      result += obj_name + "." + i + " = " + obj[i] + "\n";
   return result;
}

function treeDeposito (a_items, a_template, img_folder, target) {
	this.img_path	= img_folder;
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

	loadTemplateImagesAmbitoDeposito(a_template, img_folder);
	this.nodeOpened = function (node) {
		if (node.n_depth > 0)  {
			this.openNodes[node.n_id] = node.a_config[2];
			delete this.closedNodes[node.n_id];
		}
	}
	this.nodeClosed = function (nodeClosed) {
		this.closedNodes[nodeClosed.n_id] = nodeClosed.a_config[2];
		delete this.openNodes[nodeClosed.n_id];
	}
	this.setSelectedNode = function (path_selected_node) {
		this.path_selected_node = path_selected_node;
	}

	this.toggle = function (n_id) {
		var o_item = this.a_index[n_id];
		o_item.open(o_item.b_opened) ;
		if (o_item.b_opened)
			this.nodeOpened(o_item);
		else
			this.nodeClosed(o_item);
		setCookie('topPosAmbito', xScrollTop(null, window));

	};
	this.select = function (n_id) { return this.a_index[n_id].select(); };
	this.mout   = function (n_id) { this.a_index[n_id].upstatus(true) };
	this.mover  = function (n_id) { this.a_index[n_id].upstatus() };

	this.a_children = [];
	this.init = function() {
		for (var i = 0; i < a_items.length; i++)
			new tree_item(this, i);

		this.n_id = treesAmbitoDeposito.length;
		treesAmbitoDeposito[this.n_id] = this;

		for (var i = 0; i < this.a_children.length; i++) {
			var nodeCode = this.a_children[i].init();
			document.write(nodeCode);
			this.a_children[i].open(false,true);
		}
		var topPosAmbito = getCookie('topPosAmbito');
		if (topPosAmbito) {
			removeCookie('topPosAmbito');
			var scrollTop = eval('parseInt(topPosAmbito, 10)');
			setTimeout('window.scroll(0, '+scrollTop+')',50);
		} else {
			if (this.o_selected) {
				var idSelectedNode = 'i_divAmbitoDeposito' + this.n_id + '_' + this.o_selected.n_id;
				var selectedNodeObj = xGetElementById(idSelectedNode);
				if (selectedNodeObj && selectedNodeObj.parentElement) {
					var nodeY = xPageY(selectedNodeObj.parentElement);
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
	var o_idivID = 'i_divAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id;
	var o_idiv = get_element(o_idivID);
	if (o_idiv && this.a_config[3]) {
		if (!o_idiv.innerHTML) {
			var a_children = [];
			for (var i = 0; i < this.a_children.length; i++) {
				a_children[i]= this.a_children[i].init();
			}
			o_idiv.innerHTML = a_children.join('');
		}
		o_idiv.style.display = (this.b_opened ? 'block' : 'none');
		for (var i = 0; i < this.a_children.length; i++)
			if (this.a_children[i].b_opened) this.a_children[i].generate_content();

	}
}

function item_open (b_close, recursive) {
	if (this.a_config[3] && this.a_children.length == 0) {
		// encapsular en un ServerSideInvoker
		var treeManagerForm = document.forms['treeDepositoManagerForm'];
		treeManagerForm.actionToPerform.value = 'expandirNodo';
		treeManagerForm.node.value = this.a_config[2];
		treeManagerForm.openNodes.value = joinArray(this.o_root.openNodes, '-');
		treeManagerForm.closedNodes.value = joinArray(this.o_root.closedNodes, '-');
		if (this.o_root.o_selected)
			treeManagerForm.selectedNode.value = this.o_root.o_selected.a_config[2];
		treeManagerForm.target = self.name;
		treeManagerForm.submit();
		return;
		//window.location = "/archivo/action/manageVistaDeposito?method=expandirNodo&viewName=estructuraDeposito&node="+this.a_config[2];
	}
	this.b_opened = !b_close;
	this.generate_content();

	var o_jicon = document.images['j_imgAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id],
		o_iicon = document.images['i_imgAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id];
	if (o_jicon) o_jicon.src = this.get_icon(true);
	if (o_iicon) o_iicon.src = this.get_icon();
	if (!b_close && recursive) {
		for (var i = 0; i < this.a_children.length; i++) {
			if (this.a_children[i].b_opened)
				this.a_children[i].open(false,true);
		}
	}
	this.upstatus();
}

function item_select (b_deselect) {
	this.selected = !b_deselect;
	if (!b_deselect) {
		var o_olditem = this.o_root.o_selected;
		this.o_root.o_selected = this;
		if (o_olditem && (o_olditem.n_id != this.n_id))
			o_olditem.select(true);
	}
	var o_iicon = document.images['i_imgAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id];
	if (o_iicon) o_iicon.src = this.get_icon();
	var itemLabel = get_element('i_txt' + this.o_root.n_id + '_' + this.n_id);
	if (itemLabel) {
		itemLabel.style.fontWeight = b_deselect ? 'bold' : 'normal';
		itemLabel.style.color = b_deselect ? '#003399' : 'red';
	}

	this.upstatus();
	return false;
	//return Boolean(this.a_config[2]);
}

function item_upstatus (b_clear) {
	window.setTimeout('window.status="' + (b_clear ? '' : this.a_config[0] + (this.a_config[2] ? ' ('+ this.a_config[2] + ')' : '')) + '"', 10);
}

var clickWaiting = null;
function nodeClicked(tree_id, node_id) {
	var str = 'viewNodeContent('+tree_id+','+node_id+')';
	setTimeout(str,200);
	clickWaiting = str;
}

function nodeDoubleClicked(tree_id, node_id) {
	if (clickWaiting != null) {
		clearTimeout(clickWaiting);
		clickWaiting = null;
	}
	treesAmbitoDeposito[tree_id].toggle(node_id);
	viewNodeContent(tree_id, node_id);
}

function viewNodeContent(tree_id, node_id) {
	var tree = treesAmbitoDeposito[tree_id];
	tree.select(node_id);
	var nodeViewForm = document.forms['viewDepositoNodeForm'];
	nodeViewForm.node.value = tree.a_index[node_id].a_config[2];
	if (tree.target) {
		nodeViewForm.target = tree.target;
	}	nodeViewForm.submit();
}

function item_init () {
	var a_offset = [],
		o_current_item = this.o_parent;
	for (var i = this.n_depth; i > 1; i--) {
		a_offset[i] = '<img src="' + this.o_root.img_path + this.o_root.a_tpl[o_current_item.is_last() ? 'icon_e' : 'icon_l'] + '" border="0" align="absbottom">';
		o_current_item = o_current_item.o_parent;
	}
	var item_label = '<img src="' + this.get_icon() + '" border="0" align="absbottom" name="i_imgAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id + '" class="t' + this.o_root.n_id + 'im"> ' +this.a_config[0];
	if (this.a_config[2])
		item_label = '<a href="javascript:none()" onclick="viewNodeContent(' +this.o_root.n_id +','+this.n_id + ')" onmouseover="treesAmbitoDeposito[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="treesAmbitoDeposito[' + this.o_root.n_id + '].mout(' + this.n_id + ')" class="t' + this.o_root.n_id + 'i" id="i_txtAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id + '" style="font-weight:'+(this.selected?'normal':'bold')+';color:'+(this.selected?'red':'#003399')+'">'+item_label + '</a>';
	var result ='<table cellpadding="0" cellspacing="0" border="0"><tr><td nowrap>' + (this.n_depth ? a_offset.join('') + (this.a_config[3]
		? '<a href="javascript: treesAmbitoDeposito[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="treesAmbitoDeposito[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="treesAmbitoDeposito[' + this.o_root.n_id + '].mout(' + this.n_id + ')"><img src="' + this.get_icon(true) + '" border="0" align="absbottom" name="j_imgAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id + '"></a>'
		: '<img src="' + this.get_icon(true) + '" border="0" align="absbottom">') : '')
		+ item_label + '<div id="i_divAmbitoDeposito' + this.o_root.n_id + '_' + this.n_id + '" style="display:none"></div>' + '</td></tr></table>';
	return result;
}

function item_get_icon (b_junction) {
	var img_icon = null;
	if (b_junction) {
		if (this.a_config[3]) {
			if (this.b_opened)
				img_icon = this.o_root.a_tpl['imgs_images'][this.is_last()?'lmenos':'menos'];
			else
				img_icon = this.o_root.a_tpl['imgs_images'][this.is_last()?'lmas':'mas'];
		} else
			img_icon = this.o_root.a_tpl['imgs_images'][this.is_last()?'lnodo':'nodo'];
	} else {
		var customIcon = null;
		if (window.getNodeImage)
			customIcon = getNodeImage(this);
		img_icon = customIcon?customIcon:this.o_root.a_tpl['imgs_defaultIcons'][(this.a_config[3]?0:4)+(this.a_config[3]&&this.b_opened?2:0)+(this.selected == this?1:0)];
	}
	return img_icon.src;
	//return this.o_root.a_tpl['icon_' + ((this.n_depth ? 0 : 32) + (this.a_children.length ? 16 : 0) + (this.a_children.length && this.b_opened ? 8 : 0) + (!b_junction && this.o_root.o_selected == this ? 4 : 0) + (b_junction ? 2 : 0) + (b_junction && this.is_last() ? 1 : 0))];
	//return 'icons/empty.gif'
}

var treesAmbitoDeposito = [];
get_element = document.all ?
	function (s_id) { return document.all[s_id] } :
	function (s_id) { return document.getElementById(s_id) };
