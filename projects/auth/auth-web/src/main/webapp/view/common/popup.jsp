<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!-- 错误提示 -->
<div class="modal fade" id="popupError" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 10000;">
	<div class="modal-dialog" role="document" style="width: 500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">提示信息</h4>
			</div>
			<div class="modal-body">
				<div class="box-body">
					<div class="tip-leo">
						<p class="con1">
							<i class="fa fa-frown-o text-red"></i> <span
								id="popupErrorContent"></span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 保存成功 -->
<div class="modal fade" id="popupSuccess" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 10000;">
	<div class="modal-dialog" role="document" style="width: 500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">提示信息</h4>
			</div>
			<div class="modal-body">
				<div class="box-body">
					<div class="tip-leo">
						<div class="con2">
							<i class="fa fa-smile-o text-green"></i> <span
								id="popupSuccessContent"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 提示信息 -->
<div class="modal fade" id="popupAlert" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 10000;">
	<div class="modal-dialog" role="document" style="width: 500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">提示信息</h4>
			</div>
			<div class="modal-body">
				<div class="box-body">
					<div class="tip-leo">

						<p class="con1">
							<i class="fa fa-commenting-o text-yellow"></i> <span
								id="popupAlertContent"></span>
						</p>
						<form class="search-form">
							<div class="input-group"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>