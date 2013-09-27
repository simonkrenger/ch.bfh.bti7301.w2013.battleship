var Diary = Diary || {};
Diary.Classes = Diary.Classes || {};

Diary.Classes.TaskFilter = function(nav, table)
{
	this._nav = nav;
	this._table = table;
	this._tasks = new Array();
	this.init();
};

Diary.Classes.TaskFilter.prototype = 
{
	_nav: null,
	_table: null,
	_tasks: null,
	init : function()
	{
		var ctx = this;
		this._table.find("tr").each(function (index)
		{
			var val = $(this).attr("week");
			if (val > 0)
			{
				ctx._tasks[val] = "exists";
			}
		});
		
		for (var key in ctx._tasks)
		{
			ctx._nav.append("<li><a show-week='" + key + "'>Week " + key + "</a></li>"); 
		}
		
		$("a[show-week]").click(function() { ctx.filter(this); });
	},
	filter : function(ele)
	{
		this._nav.find("li").each(function() {
			$(this).removeClass("active");
		});
		
		var taskId = $(ele).attr("show-week");
		$(ele.parentNode).addClass("active");
		
		this._table.find("tr").each(function () {
			if (taskId == "all")
			{
				$(this).show();
			}
			else
			{
				if ($(this).attr("week") == taskId || $(this).attr("week") == "title")
				{
					$(this).show();
				}
				else
				{
					$(this).hide();
				}
			}
		});
	}
};
