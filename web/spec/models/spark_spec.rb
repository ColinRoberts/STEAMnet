# == Schema Information
#
# Table name: sparks
#
#  id           :integer          not null, primary key
#  spark_type   :string(255)
#  content_type :string(255)
#  content      :text
#  content_hash :string(255)
#  created_at   :datetime         not null
#  updated_at   :datetime         not null
#

require 'spec_helper'

describe Spark do
  
  before(:each) do
    @attr = {
      :spark_type   => "I",
      :content_type => "L",
      :content      => "http://google.com/"
    }
  end
  
  it "creates a new instance given valid attributes" do
    Spark.create!(@attr)
  end
  
  describe "validation" do
    
    it "requires a spark type" do
      spark = Spark.new(@attr)
      spark.spark_type = ""
      spark.should_not be_valid
    end
    
    it "requires a content type" do
      spark = Spark.new(@attr)
      spark.content_type = ""
      spark.should_not be_valid
    end
    
    it "requires content" do
      spark = Spark.new(@attr)
      spark.content = ""
      spark.should_not be_valid
    end
    
  end
  
  describe "content hash" do
    
    it "hashes content after saving" do
      spark = Spark.new(@attr)
      spark.save
      
      spark.content_hash.should_not be_blank
    end
    
    it "requires a unique hash" do
      spark = Spark.new(@attr)
      spark.save
      
      spark2 = Spark.new(@attr)
      spark2.spark_type = "P"
      spark2.should_not be_valid
    end
    
  end
  
  describe "idea association" do
    
    before(:each) do
      @spark = Spark.create(@attr)
      
      @i1 = FactoryGirl.create(:idea)
      @i2 = FactoryGirl.create(:idea)
      
      @i1.sparks << @spark
      @i2.sparks << @spark
    end
    
    it "has a ideas attribute" do
      @spark.should respond_to(:ideas)
    end
    
    it "has the right ideas" do
      @spark.ideas.should == [@i1, @i2]
    end
    
    it "doesn't destroy associated ideas" do
      @spark.destroy
      [@i1, @i2].each do |i|
        Idea.find_by_id(i.id).should_not be_nil
      end
    end
    
  end
  
  describe "user association" do
    
    before(:each) do
      @spark = Spark.create(@attr)
      
      @u1 = FactoryGirl.create(:user)
      @u2 = FactoryGirl.create(:user)
      
      @u1.sparks << @spark
      @u2.sparks << @spark
    end
    
    it "has a users attribute" do
      @spark.should respond_to(:users)
    end
    
    it "has the right users" do
      @spark.users.should == [@u1, @u2]
    end
    
    it "doesn't destroy associated users" do
      @spark.destroy
      [@u1, @u2].each do |u|
        User.find_by_id(u.id).should_not be_nil
      end
    end
    
  end
  
  describe "comment association" do
    
    before(:each) do
      @spark = Spark.create(@attr)
      
      @user = FactoryGirl.create(:user)
      
      @c1 = FactoryGirl.create(:comment)
      @c2 = FactoryGirl.create(:comment)
      
      @c1.user = @user
      @c2.user = @user
      
      @c1.commentable = @spark
      @c2.commentable = @spark
      
      @c1.save
      @c2.save
    end
    
    it "has an comments attribute" do
      @spark.should respond_to(:comments)
    end
    
    it "has the right comments" do
      @spark.comments.should == [@c1, @c2]
    end
    
    it "does destroy associated comments" do
      @spark.destroy
      [@c1, @c2].each do |c|
        Comment.find_by_id(c.id).should be_nil
      end
    end
    
  end
  
  describe "tag association" do
      
      before(:each) do
        @spark = FactoryGirl.create(:spark)
        
        @t1 = FactoryGirl.create(:tag)
        @t2 = FactoryGirl.create(:tag)
        
        @t1.sparks << @spark
        @t2.sparks << @spark
      end
      
      it "has a tags attribute" do
        @spark.should respond_to(:tags)
      end
      
      it "has the right tags" do
        @spark.tags.should == [@t1, @t2]
      end
      
      it "doesn't destroy associated tags" do
        @spark.destroy
        [@t1, @t2].each do |t|
          Tag.find_by_id(t.id).should_not be_nil
        end
      end
      
    end
  
end
