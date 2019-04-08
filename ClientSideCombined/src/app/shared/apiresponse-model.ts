import {HttpResponse} from '@angular/common/http';

export interface ApiResponseModel {
    url: string;
    pdfFile: HttpResponse<Blob>;

    body: HttpResponse<Blob>;
}
