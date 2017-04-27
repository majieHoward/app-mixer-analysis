/**
* 判断对象是否存在
*/
function publicJudgmentObject(data){
    if (data != "" && data != undefined && data != null) {
		return true;
	}else{
	    return false;
	}
}
/**
 * 公共方法处理checkbox全选和反选操作 start
 */
// 处理反选事件
function handleReverseOrgSelectionContent(nameOfCheckboxGroup) {
	$(nameOfCheckboxGroup).each(function() {
		$(this).prop("checked", !$(this).is(":checked"));
	});
}
// 处理全选
function handleSelectAllOrgSelectionContent(nameOfCheckboxGroup) {
	$(nameOfCheckboxGroup).prop("checked", true);
}
/**
 * 公共方法处理checkbox全选和反选操作 end
 */
/**
 * modle显示和关闭
 */
function operationPopUpModleBox(modleId, identification) {
	$('#' + modleId).modal(identification);
}
/**
 * 区分是新增还是修改的状态标识0代表新增1代表修改
 */
var operationStatusIdentifier = 0;
function changeOperationStatusIdentifier(value) {
	operationStatusIdentifier = value;
}
/**
 * 区分是否第一次点开指标选择框
 */
var tempOperationStatusIdentifier = 0;
/**
 * 区分是否第一次点开人员信息选择框
 */
var tempOperationStatusParticipants = 0;
/**
 * 机构类型选择器更改标识器0代表需要重新加载一次机构列表 1代表不许从新加载机构
 */
var _identificationOfChange = "0";
function changeIdentificationOfChange(value) {
	_identificationOfChange = value;
}
function reductionMark() {
	operationStatusIdentifier = 0;
	tempOperationStatusIdentifier = 0;
	tempOperationStatusParticipants = 0;
	_identificationOfChange = "0";
}
/**
 * 公共ajax请求方法
 * 
 * @param url
 * @param isAsync
 * @param sendType
 * @param typeOfData
 * @param paramData
 * @param successCallback
 * @param failCallback
 * @returns
 */
function publicAjaxRequest(url, isAsync, sendType, typeOfData, paramData,
		successCallback, failCallback) {
	var dataItemReturn;
	if (isAsync == "" || isAsync == undefined || isAsync == null) {
		isAsync = false;
	}
	if (sendType == "" || sendType == undefined || sendType == null) {
		sendType = "POST";
	}
	if (paramData == "" || paramData == undefined || paramData == null) {
		paramData = "{}";
	}
	if (typeOfData == "" || typeOfData == undefined || typeOfData == null) {
		typeOfData = "json";
	}
	$.ajax({
		async : isAsync,
		type : sendType,
		dataType : typeOfData,
		data : paramData,
		url : url,
		success : function(data, resp, XMLHttpRequest) {
			// 表明是调用成功
			if (XMLHttpRequest.status == 200 && data != null) {
				// 不需要提示消息
				if (successCallback != null) {
					successCallback(data);
				} else {
					dataItemReturn = data;
				}
			} else if (XMLHttpRequest.status == 0) {
				alert('数据请求失败。');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (failCallback != null) {
				failCallback(data);
			} else {
                
			}
		}
	});
	return dataItemReturn;
}
function publicFuncJudgmentObject(paramObject) {
	if (typeof (paramObject) != "undefined" && paramObject != null) {
		return true;
	} else {
		return false;
	}
}
// 公用弹出框
function buildInformationModal(data, hideFunctionSuccess,
		hiddenFunctionSuccess, hideFunctionError, hiddenFunctionError) {
	if (publicFuncJudgmentObject(data)
			&& publicFuncJudgmentObject(data.stepMessage)
			&& publicFuncJudgmentObject(data.stepMessage.stepMsgItems)) {

		var isSuccess = data.isSuccess;
		$("#returnMsgStateIdentification").removeClass("errorWarning")
				.removeClass("successWarning");
		$("#returnMsgStateIdentification").addClass(isSuccess + "Warning");
		$("#returnMsgException").text(data.returnMsg);
		var stepMessage = data.stepMessage;
		var stepMsgItems = [];
		stepMsgItems = stepMessage.stepMsgItems;
		_informationModalInputHtml = "";
		var stepMsgItemsInfo;
		var stepInfoIdentification;
		var stepInfoKey;
		var stepInfoMsg;
		var classInput;
		for (i = 0; i < stepMsgItems.length; i++) {
			stepMsgItemsInfo = stepMsgItems[i];
			stepInfoIdentification = stepMsgItemsInfo.stepInfoIdentification;// "stepInfoIdentification":
			// //
			// "success",
			stepInfoKey = stepMsgItemsInfo.stepInfoKey;// "stepInfoKey": //
			stepInfoMsg = stepMsgItemsInfo.stepInfoMsg; // "stepInfoMsg":
			classInput = "point-time point-" + stepInfoIdentification;
			_informationModalInputHtml += '<section><span class="'
					+ classInput
					+ '"></span>'
					+ '<time><span>'
					+ stepInfoKey
					+ '</span></time>'
					+ '<aside><p class="things">'
					+ stepInfoMsg
					+ '</p><p class="brief"><span class="text-green"></span></p></aside>'
					+ '</section>'
		}
		$("#returnMsgInformationModalInput").html(_informationModalInputHtml);
		/**
		 * 当调用 hide 实例方法时触发
		 */
		$('#returnMsgInformationModal').on('hide.bs.modal', function() {
			if ("success" == isSuccess && hideFunctionSuccess != null) {
				hideFunctionSuccess();
			}
			if ("error" == isSuccess && hideFunctionError != null) {
				hideFunctionSuccess();
			}
		});
		/**
		 * 当模态框完全对用户隐藏时触发
		 */
		$('#returnMsgInformationModal').on('hidden.bs.modal', function() {
			if ("success" == isSuccess && hiddenFunctionSuccess != null) {
				hiddenFunctionSuccess();
			}
			if ("error" == isSuccess && hiddenFunctionError != null) {
				hiddenFunctionError();
			}
		});
	}
	operationPopUpModleBox("returnMsgInformationModal", 'show');
}