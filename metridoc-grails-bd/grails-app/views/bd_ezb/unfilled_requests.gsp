%{--
  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  - 	Educational Community License, Version 2.0 (the "License"); you may
  - 	not use this file except in compliance with the License. You may
  - 	obtain a copy of the License at
  - 
  - http://www.osedu.org/licenses/ECL-2.0
  - 
  - 	Unless required by applicable law or agreed to in writing,
  - 	software distributed under the License is distributed on an "AS IS"
  - 	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  - 	or implied. See the License for the specific language governing
  - 	permissions and limitations under the License.  --}%

<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="bd_main" />
<title>Unfilled Requests</title>
</head>
<body>
	<div class="body">
	<g:if test="${ reportData.size() > 0 }">
		<table class="list" cellspacing="0">
			<tr>
				<th>Lender</th>
				<th>Title</th>
				<th>CallNo</th>
				<th>Publication Year</th>
				<th>Isbn</th>
			</tr>
			<g:each var="reportItem" status="i" in="${reportData}">
				<tr class="${ (i % 2) == 0 ? 'even' : 'odd'}">
					<td>${reportItem.lender != null ? reportItem.lender : 'N/A' }</td>
					<td>${reportItem.title}</td>
					<td>${reportItem.callNo}</td>
					<td>${reportItem.publicationYear}</td>
					<td>${reportItem.isbn}</td>
				</tr>
			</g:each>
		</table>
		</g:if>
		<g:else>
			<div>There are no unfilled requests for requested date range.</div>
		</g:else>
	</div>
</body>
</html>