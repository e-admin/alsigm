<script>
	function save()
	{
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do"
		document.defaultForm.submit();
	}

	var block = 1;

	function showTab(i)
	{
		document.getElementById('block'+ block).style.display='none';
		document.getElementById('link'+ block).className="unselect";
		document.getElementById('tdlink'+ block).className="unselect";
		document.getElementById('block'+ i).style.display='block';
		document.getElementById('link'+ i).className = 'select';
		document.getElementById('tdlink'+ i).className = 'select';
		// calculamos la url correcta
		var url = (document.location.href).split("#")[0];
		document.getElementById('link'+ i).href = url+'#target'+i;
		block = i;
	}
</script>