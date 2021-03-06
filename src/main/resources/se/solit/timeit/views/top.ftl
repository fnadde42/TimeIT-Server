<!--[if lt IE 9]>
<div  class="browserOverlay">
</div>
<div id="browser-warning">
		<p>Upgrade to a modern browser.</p>
</div>
<![endif]-->
<div id="toolbar">
<#if currentUser?? >
<div id="menu">
<a href="/" class="${getClasses("home")}">Home</a>
${reportLink}
<#if currentUser.hasRole("Admin")>
<a href="/user/" class="${getClasses('admin')}">Admin</a>
</#if>
</div>
<div id="userinfo">
	<a href="/user/${currentUser.username?html}" class="${getClasses("user")}">
	<#if currentUser.name?? && currentUser.name?length gt 0>
		${currentUser.name?html}
	<#else>
		${currentUser.username?html}
	</#if>
	</a>
</div>
</#if>
</div>
<div id="content">
		<p>
		<#if message?length gt 0>
			<div id="message">${message}
			</div>
		</#if>
		</p>
