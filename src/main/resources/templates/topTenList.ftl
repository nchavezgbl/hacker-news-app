<!DOCTYPE html>
<html>
   <head>
      <title>Top Ten Story List</title>
      <link rel="stylesheet" type="text/css" href="css/style.css">
   </head>
   <body>
     <h3>Top Ten Hacker News</h3>
     <br><br>
      <div>

         <table border="1">
            <tr>
               <th>Rank</th>
               <th>Title</th>
            </tr>
            <#list topTenList as story>
            <tr>
               <td>${story?counter}</td>
               <td><a href="${story.url}">${story.title}</a></td>
            </tr>
            </#list>
         </table>
      </div>
   </body>
</html>