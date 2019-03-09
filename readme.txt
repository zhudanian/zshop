web综合案例
使用技术：spring  springmvc  mybatis maven  bootstrap

采用多模块开发
将项目按照层次进行划分

zshop_parent   			父模块，用于管理jar包,pom模块
zshop_common   			通用模块,写一个工具类..
zshop_pojo     			pojo模块
zshop_dao      			dao模块
zshop_service  			service模块
zshop_front_web  		前台web模块
zshop_backend_web  		后台web模块

表单--->controller--->serivce--->dao
       (VO)--------->dto(vo)---->pojo





