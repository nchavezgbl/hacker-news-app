<#import "/spring.ftl" as spring/>

<html>
   <head>
      <title>Person List</title>
      <link rel="stylesheet"
           type="text/css"
           href="<@spring.url '/css/style.css'/>"/>
   </head>
   <body>
     <h3>Top Ten Hacker News</h3>
     <br><br>
      <div>

         <table border="1">
            <tr>
               <th>ID</th>
               <th>Title</th>
               <th>Link</th>
            </tr>
            <#list topTenStories as story>
            <tr>
               <td>${story.id}</td>
               <td>${story.title}</td>
               <td>${story.url}</td>
            </tr>
            </#list>
         </table>
      </div>
   </body>
</html>