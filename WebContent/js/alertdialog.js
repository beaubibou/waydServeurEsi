
function affichePoPupAction(titre,message,action) {

			BootstrapDialog.show({
				title : titre,
				message : message,
				buttons : [

				{
					label : 'Oui',
					action : function(dialog) {
						location.href = action;
						dialog.close();
					}
				}

				]
			});

		}
function affichePoPup(titre,message) {

	BootstrapDialog.show({
		title : titre,
		message : message,
		buttons : [

		{
			label : 'Ok',
			action : function(dialog) {
				dialog.close();
			}
		}

		]
	});

}
