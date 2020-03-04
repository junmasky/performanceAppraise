/**
 * 
 */

function dayReportViewModel(options) {
    var self = this;

    /**Binding Fields*/
    /*self.site_id = ko.observable();
    self.rack_no = ko.observable();
    self.host_name = ko.observable();
    self.serial_no = ko.observable();
    self.ip_address = ko.observable();
    self.mac_address = ko.observable();
    self.application_module = ko.observable();

    self.listSize = ko.observable();
    self.chosenSite_id = ko.observable();
    self.chosenRack_no = ko.observable();
    self.isSearch = ko.observable();
    self.isSearch(false);*/
   
    /**Binding List*/
    /*if (options) {
        self.headers = ko.observableArray(options.headers);
    }*/
  
    self.list = ko.observableArray();


    /**Binding Functions*/
   
    self.reset = function(){
    	self.list([]);
    	return true;
    }
   
}