<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
    <asset:stylesheet href="bootstrap-editable.css"/>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2"></div>

        <div class="col-md-8">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${products}" var="product" status="i">
                    <tr>
                        <td>${i + 1}</td>
                        <td>
                            <a href="#" class="product_${product?.uniqueid}">
                                ${product?.name}
                            </a>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        <div class="col-md-2"></div>
    </div>
</div>
<asset:javascript src="bootstrap-editable.min.js"/>
<script>
    $(document).ready(function () {
        $.fn.editable.defaults.mode = 'inline';
        $("a[class^='product']").each(function () {
            var currentId = $(this).attr("class").replace("product_", "");
            console.log(currentId);
            $(this).editable({
                type: 'text',
                pk: currentId,
                url: '${createLink(controller: 'product',action: 'updateName')}',
                title: 'Enter Product Name'
            });
        });
    });
</script>
</body>
</html>
