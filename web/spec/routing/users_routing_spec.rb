require 'spec_helper'

describe "routes for Users" do
  
  it "routes get /api/v1/users.json to Users controller" do
    { :get => "/api/v1/users.json" }.should route_to(
      :controller => "v1/users",
      :action => "index",
      :format => "json"
    )
  end
  
  it "routes post /api/v1/users.json to Users controller" do
    { :post => "/api/v1/users.json" }.should route_to(
      :controller => "v1/users",
      :action => "create",
      :format => "json"
    )
  end
  
  it "routes get /api/v1/users/:id.json to Users controller" do
    { :get => "/api/v1/users/1.json" }.should route_to(
      :controller => "v1/users",
      :action => "show",
      :id => "1",
      :format => "json"
    )
  end
  
  it "routes put /api/v1/users/:id.json to Users controller" do
    { :put => "/api/v1/users/1.json" }.should route_to(
      :controller => "v1/users",
      :action => "update",
      :id => "1",
      :format => "json"
    )
  end
  
  it "routes delete /api/v1/users/:id.json to Users controller" do
    { :delete => "/api/v1/users/1.json" }.should route_to(
      :controller => "v1/users",
      :action => "destroy",
      :id => "1",
      :format => "json"
    )
  end
  
end