Steamnet::Application.routes.draw do
  
  scope "api" do
    
    namespace :v1 do
      
      resources :ideas, :sparks, :except => [:new, :edit, :update] do
        resources :comments, :except => [:new, :edit, :update]
      end
      
      resources :users, :except => [:new, :edit]
      
      resources :tags, :only => [:index, :show]
      
      get "jawns" => "jawns#index"
      
    end
    
  end
  
end

#== Route Map
# Generated on 20 Jun 2013 19:42
#
#                   POST   /api/v1/ideas/:idea_id/comments(.:format)       v1/comments#create
#   v1_idea_comment GET    /api/v1/ideas/:idea_id/comments/:id(.:format)   v1/comments#show
#                   DELETE /api/v1/ideas/:idea_id/comments/:id(.:format)   v1/comments#destroy
#          v1_ideas GET    /api/v1/ideas(.:format)                         v1/ideas#index
#                   POST   /api/v1/ideas(.:format)                         v1/ideas#create
#           v1_idea GET    /api/v1/ideas/:id(.:format)                     v1/ideas#show
#                   DELETE /api/v1/ideas/:id(.:format)                     v1/ideas#destroy
# v1_spark_comments GET    /api/v1/sparks/:spark_id/comments(.:format)     v1/comments#index
#                   POST   /api/v1/sparks/:spark_id/comments(.:format)     v1/comments#create
#  v1_spark_comment GET    /api/v1/sparks/:spark_id/comments/:id(.:format) v1/comments#show
#                   DELETE /api/v1/sparks/:spark_id/comments/:id(.:format) v1/comments#destroy
#         v1_sparks GET    /api/v1/sparks(.:format)                        v1/sparks#index
#                   POST   /api/v1/sparks(.:format)                        v1/sparks#create
#          v1_spark GET    /api/v1/sparks/:id(.:format)                    v1/sparks#show
#                   DELETE /api/v1/sparks/:id(.:format)                    v1/sparks#destroy
#          v1_users GET    /api/v1/users(.:format)                         v1/users#index
#                   POST   /api/v1/users(.:format)                         v1/users#create
#           v1_user GET    /api/v1/users/:id(.:format)                     v1/users#show
#                   PUT    /api/v1/users/:id(.:format)                     v1/users#update
#                   DELETE /api/v1/users/:id(.:format)                     v1/users#destroy
#           v1_tags GET    /api/v1/tags(.:format)                          v1/tags#index
#            v1_tag GET    /api/v1/tags/:id(.:format)                      v1/tags#show
#          v1_jawns GET    /api/v1/jawns(.:format)                         v1/jawns#index
