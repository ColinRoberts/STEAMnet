require 'spec_helper'

describe "routes for Tags" do
  
  it "routes get /api/v1/tags.json to Tags controller" do
    { :get => "/api/v1/tags.json" }.should route_to(
      :controller => "v1/tags",
      :action => "index",
      :format => "json"
    )
  end
  
  it "routes get /api/v1/tags/:id.json to Tags controller" do
    { :get => "/api/v1/tags/1.json" }.should route_to(
      :controller => "v1/tags",
      :action => "show",
      :id => "1",
      :format => "json"
    )
  end
  
end