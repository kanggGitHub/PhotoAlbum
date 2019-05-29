package com.kg.PhotoAlbum;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class CodeGeneration {

      public static void main(String[] args) {
            AutoGenerator mpg = new AutoGenerator();

            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            String projectPath = System.getProperty("user.dir");
            gc.setOutputDir(projectPath +"/src/main/java");//输出文件路径
            gc.setOpen(false); //创建完成后是否打开文件目录
            gc.setFileOverride(true);
            gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
            gc.setEnableCache(false);// XML 二级缓存
            gc.setBaseResultMap(true);// XML ResultMap
            gc.setBaseColumnList(false);// XML columList
            gc.setAuthor("kg");// 作者

            // 自定义文件命名，注意 %s 会自动填充表实体属性！
            gc.setControllerName("%sController");
            gc.setServiceName("%sService");
            gc.setServiceImplName("%sServiceImpl");
            gc.setMapperName("%sMapper");
            //gc.setXmlName("%sMapper");
            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setDbType(DbType.MYSQL);
            dsc.setDriverName("com.mysql.cj.jdbc.Driver");
            dsc.setUsername("root");
            dsc.setPassword("root");
            dsc.setUrl("jdbc:mysql://127.0.0.1:3306/springboot");
            mpg.setDataSource(dsc);




            // 包配置
            PackageConfig pc = new PackageConfig();
            pc.setParent("com.kg.PhotoAlbum");
            pc.setController("controller");
            pc.setService("service");
            pc.setServiceImpl("service.impl");
            pc.setMapper("dao");
//            pc.setXml("xml");
            pc.setEntity("model");

            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                  @Override
                  public void initMap() {
                        // to do nothing
                  }
            };

            // 如果模板引擎是 freemarker
            //String templatePath = "/templates/mapper.xml.ftl";
            // 如果模板引擎是 velocity
            String templatePath = "/templates/mapper.xml.vm";

            // 自定义输出配置
            List<FileOutConfig> focList = new ArrayList<>();
            // 自定义配置会被优先输出
            focList.add(new FileOutConfig(templatePath)  {
                  @Override
                  public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath + "/src/main/resources/static/mapper/"
                              + "/" + tableInfo.getEntityName() + "Mapper.xml";
                  }
            });


            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);

            // 配置模板
            TemplateConfig templateConfig = new TemplateConfig();

            // 配置自定义输出模板
            //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
            // templateConfig.setEntity("templates/entity2.java");
            // templateConfig.setService();
            // templateConfig.setController();

            templateConfig.setXml(null);
            mpg.setTemplate(templateConfig);


            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            // strategy.setTablePrefix(new String[] { "sys_" });// 此处可以修改为您的表前缀
            strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
            strategy.setInclude(new String[] { "user" }); // 需要生成的表

            strategy.setSuperServiceClass(null);
            strategy.setSuperServiceImplClass(null);
            strategy.setSuperMapperClass(null);

            mpg.setStrategy(strategy);

            // 执行生成
            mpg.execute();

      }

}
