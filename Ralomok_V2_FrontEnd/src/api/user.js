import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function regist(param, success, fail) {
	// local.defaults.headers["Authorization"] =
	// sessionStorage.getItem("accessToken");
	await local.post(`/user/regist`, param).then(success).catch(fail);
}

export {
	regist,
};
