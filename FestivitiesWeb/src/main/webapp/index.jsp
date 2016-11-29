<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="Prueba de carga de archivo">
<meta name="author" content="Lisset Murcia">
<title>Pantalla de Prueba de Carga de Archivo</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/estilos.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/UrlGlobal.js"></script>
<script type="text/javascript">
$().ready(function() {
	country = 'CO';
	languague = 'sp';
	
	var btnCargarArchivo = $('#WIN_CARGAR_ARCHIVO');
	btnCargarArchivo.click(ValidacionArchivo);

	var panelResultado = $('#panelResultado');
	panelResultado.hide();

	var panelPestanias = $('#panelPestanias');
	panelPestanias.hide();

	var linkJson = $('#linkJson'),
		linkPlain = $('#linkPlain');

	var panelJsonShow = $('#panelJsonShow');
	panelJsonShow.hide();

	linkJson.click({tipo:'JSON'},ConvertirResultado);
	linkPlain.click({tipo:'PLAIN'},ConvertirResultado);
	
	var btnLinkedIn = $('#btnLinkedIn');
	btnLinkedIn.click(function(){
		window.open('https://www.linkedin.com/in/lisset-murcia-908a68ba', '_blank');
	});
	
	var btnLinkedIn = $('#gitLink');
	btnLinkedIn.click(function(){
		window.open('https://github.com/yliss/festividades ', '_blank');
	});
	
	var btnSpec = $('#WIN_CARGAR_ESPECIFICACIONES');
	btnSpec.click(function(){
		window.open(urlGlobal + 'application.wadl', '_blank');
	});
	
	CargarMensajes();
	
	var selectNationaliti = $('#selectNationaliti');
	
	selectNationaliti.change(ChageLanguague);
});

var country;
var languague;

function ChageLanguague(){
	var selectNationaliti = $('#selectNationaliti');
	var hdnCountry = $('#hdnCountry');
	var hdnLanguague = $('#hdnLanguague');
	var value = selectNationaliti.val();
	
	languague = value;
	
	hdnLanguague.val(languague);
	
	CargarMensajes();
}

function ConvertirResultado(event){
	var tipo = event.data.tipo;
	var resultadoValidacion = $('#resultadoValidacion');
	var linkJson = $('#linkJson');
	var linkPlain = $('#linkPlain');
	var panelJsonShow = $('#panelJsonShow');
	
	var liJson = linkJson.parent();
	var liPlain = linkPlain.parent();
	
	switch(tipo)
	{
		case 'JSON':
			resultadoValidacion.hide();
			panelJsonShow.show();
			var width = resultadoValidacion.width();
			var height = resultadoValidacion.height();

			panelJsonShow.css('width',width + 'px');
			panelJsonShow.css('height',height + 'px');
			panelJsonShow.css('overflow-y','scroll');
			panelJsonShow.css('overflow-x','hidden');
			
			liJson.addClass('active');
			liPlain.removeClass('active');
			panelJsonShow.html(json);
			break;
		case 'PLAIN':
			panelJsonShow.css('width','');
			panelJsonShow.css('height','');
			panelJsonShow.css('overflow-y','');
			panelJsonShow.css('overflow-x','');
			panelJsonShow.hide();
			resultadoValidacion.show();
			liPlain.addClass('active');
			liJson.removeClass('active');
			resultadoValidacion.html(plain);
			break;
	}
}

function CargarMensajes()
{
	var url = urlGlobal + 'Festivities/MessagesApp/';
	
	url += country + '&' + languague;
	
	$.ajax({
		url: url,
		type: 'GET',
		data: '',
		contentType: 'application/json',
		accepts: "application/json",
		headers: { 
			Accept : "application/json"
		},
		cache: false,
		processData:false,
		success:CargarMensajesSucess,
		error: ErroresGenericos
	});
}

function CargarMensajesSucess(data, textStatu)
{
	$(data.messagesWindow).each(function(index){
		console.log(this);
		console.log(this.name + ' ' + this.value);
		
		var name = this.name;
		var value = this.value;
		
		$('#' + name).text(value);
	});
}

function ValidacionArchivo(){
	var b = true;
	var fleXpdl = $('#fleXpdl');
	var panelMensajesBody = $('#panelMensajesBody');
	var panelMensajes = $('#panelMensajes');

	if(fleXpdl.val() === '')
	{
		b = false;
		panelMensajesBody.html('El campo Archivo no puede estar vacio');
		panelMensajes.modal('show');
	}
	
	if(b)
	{
		var formElement = document.getElementById("frmArchivos");
		
		var url = urlGlobal + 'Festivities/LoadFile';

		var data = new FormData(formElement);
		
		$.ajax({
			url: url,
			type: 'POST',
			data: data,
			contentType: false,
			accepts: "application/json",
			headers: { 
				Accept : "application/json"
			},
			cache: false,
			processData:false,
			success:ValidacionArchivoSucess,
			error: ErroresGenericos
		});

		plain = '';
		json = '';
	}
}

var plain;
var json;
function ValidacionArchivoSucess(data, textStatu){
	var panelResultado = $('#panelResultado');
	var resultadoValidacion = $('#resultadoValidacion');
	var panelJsonShow = $('#panelJsonShow');
	var panelMensajesBody = $('#panelMensajesBody');
	var panelMensajes = $('#panelMensajes');
	var panelPestanias = $('#panelPestanias');

	var linkJson = $('#linkJson');
	var linkPlain = $('#linkPlain');
	
	var liJson = linkJson.parent();
	var liPlain = linkPlain.parent();
	
	liPlain.addClass('active');
	liJson.removeClass('active');

	panelJsonShow.html('');
	panelJsonShow.hide();
	
	var message = data.message;
		
	plain = JSON.stringify(message);
	json = syntaxHighlight(JSON.stringify(message));

	resultadoValidacion.html(plain);
	
	resultadoValidacion.show();
	panelResultado.show();
	panelPestanias.show();
}

function ErroresGenericos(textStatus, errorThrown) {
	alert(errorThrown + textStatus);
}

function syntaxHighlight(json) {
    if (typeof json != 'string') {
         json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}

</script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" id="WIN_LINK_DESCRIPTOR" href="#">Validador de Xpdl</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <!--
          <ul class="nav navbar-nav">
            <li><a href="#about">About</a></li>
          </ul>
          -->
			<ul class="nav navbar-nav navbar-right">
				<li class="navbar-right">
          			<a href="#" id="gitLink" class="button" style="margin-top:-4px;margin-right:25px;">
          				<svg aria-hidden="true" class="octicon octicon-mark-github" height="32" version="1.1" viewBox="0 0 16 16" width="32"><path fill-rule="evenodd" d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z"></path></svg>
          			</a>
				</li>
            	<li class="navbar-right">
          			<a class="banner-logo-container" href="#" id="btnLinkedIn">
						<h2 id="in-logo" class="logo" style="margin-left: 10px;"><span>LinkedIn</span></h2>
					</a>
				</li>
				<li class="navbar-right">
          			<select id="selectNationaliti" style="margin-top:15px;">
          				<option value="sp" >Spanish</option>
          				<option value="en" >English</option>
          			</select>
				</li>
			</ul>
        </div>
      </div>
    </nav>

    <div class="container">

      <div class="starter-template">
        <h1 id="WIN_TITLE" >Cliente Rest Para Cargar Archivo Festividades</h1>
        <p class="lead" id="WIN_DESCRIPCION" >Puedes cargar un archivo xpdl y nosotros te retornaremos el resultado de las validaciones</p>
      </div>
	 <br/>
		<div class="row" >
			<div class="col-md-12" >
				<div class="row" >
					<div class="col-md-1" ></div>
					<div class="col-md-10" >
						<div class="row" >
							<div class="col-md-2" >
								<button type="button" id="WIN_CARGAR_ARCHIVO" class="btn btn-success" >Validar Archivo</button>
							</div>
							<div class="col-md-2" >
								<button type="button" id="WIN_CARGAR_ESPECIFICACIONES" class="btn btn-warning" >Especificaciones Del Servicio</button>
							</div>
							<div class="col-md-10" ></div>
						</div>
						<div class="row" >
							<div class="col-md-2" >
								<form method="post" id="frmArchivos">
									<div class="form-group">
									  <label for="fleXpdl" id="WIN_LABEL_FILE">Archivo:</label>
									  <input type="file" id="fleXpdl" name="fleXpdl" value="" />
									  <input type="hidden" id="hdnCountry" name="hdnCountry" value="CO" />
									  <input type="hidden" id="hdnLanguague" name="hdnLanguague" value="sp" />
									</div>
								</form>
							</div>
							<div class="col-md-10" ></div>
						</div>
						<div class="row" id="panelPestanias">
							<div class="col-md-2" ></div>
							<div class="col-md-8" >
								<ul class="nav nav-pills">
							      <li class="active"><a href="#" id="linkPlain">Plain</a></li>
							      <li><a href="#" id="linkJson" >Json</a></li>
							    </ul>
							</div>
							<div class="col-md-2" ></div>
						</div>
						<div class="row" id="panelResultado">
							<div class="col-md-2" ></div>
							<div class="col-md-8" >
								<textarea id="resultadoValidacion" class="form-control" rows="5" readonly="readonly"></textarea>
								<div id="panelJsonShow" style="border: 1px solid #ccc;">
								</div>
							</div>
							<div class="col-md-2" >
							</div>
						</div>
					</div>
					<div class="col-md-1" ></div>
				</div>
			</div>
		</div>
	
    </div>
    
    <div class="modal fade" id="panelMensajes" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<div class="centered text-center" style="margin-top: 0px;">            
					   <div class="btn-group" role="group" aria-label="...">
						  <div class="btn-group" role="group">	     
							 <IMG SRC="images/logo_mensaje.png" class="img-responsive" WIDTH=50 HEIGHT=50 style="margin-top: 6px;"/>
						  </div>
						  <div class="btn-group" role="group" style="margin-top: 0px;">
							 <h4 id="Heading" style="font-family: photographs;letter-spacing: 3px;
								     font-style: oblique;font-weight: bold;font-size: 30px;text-transform: uppercase;">Mensaje</h4>
						  </div>
						</div>	
					</div>
				</div>
					<div class="modal-body" id="panelMensajesBody">
					</div>
					<div class="modal-footer ">
						<button type="button" class="btn btn-primary" data-dismiss="modal" data-backdrop="false" id="btnEliminarAceptar">Acepto</button>
					</div>
			</div>
		</div>
	</div>
</body>
</html>