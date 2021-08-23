function getmeetingdetail(id){
	
   location.href='getMeetingSimple?id='+id;
}
let meetingID = 0;
//console.clear(); // clear console spam while writing
function getmeetingdetail(id){
   $('#MoaModal1 .modal-content').load("getMeetingSimple?id="+id);
   $('#MoaModal1').modal();
}


(function($) {
	
  'use strict';

  var _class = 'cmenu';

  /** 
   * @var {this} Static instance of plugin 
   */
  var _that = null;

  var _default = {
    items: [],
    disabled: []
  };

  /**
   * @description Constructor for the google button plugin
   * @param {DOM element} the selected element
   * @param {object} the contextmenu options
   */
  function Plugin(element, options) {
    this.init(element, options);
  }

  Plugin.prototype = {
    /** 
     * @description Initializes the plugin on the selected element
     * @param {DOM element} the selected element
     * @param {object} the contextmenu options
     */
    init: function(element, options) {
      _that = this;
      this.active = false;
      this.prevent_close = false;
      this.parent = $(element);
      this.options = $.extend(_default, options);
      this.set_click_event();
    },

    /**
     * @description Set the right click event
     */
    set_click_event: function() {
      var that = this;
      this.parent.contextmenu(function(e) {
		meetingID= $(this).attr("id");
	
        e.preventDefault();

        _that.remove_all();
        var menu = that.create_menu(that.parent, $(document.createElement('div')).addClass(_class), that);

        $('body').append(menu.css({
          left: e.pageX,
          top: e.pageY
        }));

        $(document).click(function(event) {
	
          if (menu.is(":hover")) {
            if (that.prevent_close) {
              that.prevent_close = false
              return;
            }

            var timer = setInterval(function() {
	
              menu.remove();
              clearInterval(timer);
            }, 300);
          } else menu.remove();
        });
      });
    },

    /**
     * @description Creates the contextmenu
     * @param {DOM element} the clicked element
     * @param {DOM element} the contextmenu body
     */
    create_menu: function(parent, body, plugin) {
	
      var options = this.options;
      $.each(options.items, function() {
        var disabled = false;
        if (options.disabled !== undefined) {
          for (var i = 0; i < options.disabled.length; i++) {
            if (!disabled)
              disabled = options.disabled[i].toLowerCase() == this.title.toLowerCase();
          }
        }
        var item = $(document.createElement('div')).addClass('item');
        var label = $(document.createElement('div')).addClass('label');
        var ripple = $(document.createElement('paper-ripple')).attr('fit', '');
        item.data({
          'disabled': disabled,
          'cmenu': plugin,
          'link': parent,
          'callback': options[this.fn]
        });
        item.append(label.html(this.title)).click(plugin.on_item_click);

        if (disabled)
          item.addClass('disabled');
        else
          item.append(ripple);

        body.append(item.prop('title', this.message));
      });
      body.contextmenu(function(e) {
        e.preventDefault()
      });
      return body;
    },

    /**
     * @description Removes all the contextmenu's from the body
     */
    remove_all: function() {
      this.prevent_close = false;
      $('body').find('.' + _class).remove();
    },

    /**
     * @description Sets the state in to prevent closing on click
     * @param {bool} flag
     * @return this
     */
    set_prevent_closing: function(flag) {
      this.prevent_close = flag;

      return this;
    },

    on_item_click: function(callback) {
	
      var data = $(this).data();
      if (data !== undefined) {

        var plugin = data['cmenu'];
        var disabled = data['disabled'] !== undefined ? data['disabled'] : false;
        if (plugin !== undefined)
          plugin.prevent_close = disabled

        if (!disabled && data['callback'] !== undefined && typeof data['callback'] === 'function') {
          if (data['executed'] === undefined || !data['executed']) {
            data['callback']();
            $(this).data('executed', true);
          }
        }
      }
    }
  };

  $.fn.cmenu = function(options) {
	
    if (!$(this).data(_class)) $(this).data(_class, new Plugin(this, options));
    return $(this).data(_class);
  }
})(jQuery);

$('.folderRightClick').cmenu({
  items: [{
    title: 'Preview',
    message: 'Click to open the preview in a new window',
    icon: 'fa fa-eye',
    fn: 'on_preview'
  },  {
    title: 'WasteBasket',
    message: 'Click to delete this object',
    icon: 'fa fa-trash-o',
    fn: 'on_delete'
  },  {
	title: 'MoveMeeting',
    message: 'Click to move this object',
    icon: 'fa fa-trash-o',
    fn: 'on_move'
	}],
  on_preview: function() {
	let url = 'meetingdetail?meetingId='+meetingID;
  },
  
  on_delete: function() {
	$.post("istrashfile",
			{meetingID},function(){
			location.href='folderList';
					});
},
  on_move: function(){
	location.href='movefolderList?id='+meetingID;
}
});