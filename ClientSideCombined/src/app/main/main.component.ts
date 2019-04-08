import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {PDFDocumentProxy, PDFProgressData, PDFSource, PdfViewerComponent} from 'ng2-pdf-viewer';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {Router} from '@angular/router';
import {ComparejobdataService} from '../shared/comparejobdata.service';
import {ApiResponseModel} from '../shared/apiresponse-model';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
    form: FormGroup;
    loading = false;

    @ViewChild('fileInput1') fileInput1: ElementRef;
    @ViewChild('fileInput2') fileInput2: ElementRef;

    pdfSrc1: string | PDFSource | ArrayBuffer = '';
    pdfSrc2: string | PDFSource | ArrayBuffer = '';

    pdf1 = 'pdfSrc1';
    pdf2 = 'pdfSrc2';

    error: any;
    page = 1;
    rotation = 0;
    zoom = 1;
    originalSize = false;
    pdf: any;
    renderText = true;
    progressData: PDFProgressData;
    isLoaded = false;
    stickToPage = false;
    showAll = true;
    autoresize = true;
    fitToPage = false;
    outline: any[];
    renderTextMode = 2;

    @ViewChild(PdfViewerComponent) private pdfComponent: PdfViewerComponent;


    ngOnInit(): void {
    }

    constructor(private fb: FormBuilder, private http: HttpClient, private router: Router,
                private comparejobdataService: ComparejobdataService) {
        this.createForm();
    }


    /**
     * Render PDF preview on selecting file
     */
    onFileSelected(pdfSource) {
        let $pdf: any;

        if (pdfSource === 'pdfSrc1') {
            $pdf = document.querySelector('#file1');
        } else {
            $pdf = document.querySelector('#file2');
        }

        if (typeof FileReader !== 'undefined') {
            const reader = new FileReader();

            reader.onload = (e: any) => {
                if (pdfSource === 'pdfSrc1') {
                    this.pdfSrc1 = e.target.result;
                } else {
                    this.pdfSrc2 = e.target.result;
                }

            };
            reader.readAsArrayBuffer($pdf.files[0]);
        }
    }

    /**
     * Get pdf information after it's loaded
     */
    afterLoadComplete(pdf: PDFDocumentProxy) {
        this.pdf = pdf;
        this.isLoaded = true;
        this.loadOutline();
    }

    /**
     * Get outline
     */
    loadOutline() {
        this.pdf.getOutline().then((outline: any[]) => {
            this.outline = outline;
        });
    }

    /**
     * Handle error callback
     */
    onError(error: any) {
        this.error = error; // set error
    }


    /**
     * Pdf loading progress callback
     */
    onProgress(progressData: PDFProgressData) {
        // console.log('Progress Data: ', progressData);
        this.progressData = progressData;
        this.isLoaded = false;
        this.error = null; // clear error
    }

    getInt(value: number): number {
        return Math.round(value);
    }

    /**
     * Page rendered callback, which is called when a page is rendered (called multiple times)
     */
    pageRendered(e: CustomEvent) {
        // console.log('(page-rendered)', e);
    }

    /**
     * File Upload Section
     */

    createForm() {
        this.form = this.fb.group({
            file1: [null, Validators.required],
            file2: [null, Validators.required]
        });
    }

    onFileChange1(event) {
        if (event.target.files.length > 0) {
            const file1 = event.target.files[0];
            this.form.get('file1').setValue(file1);
        }
    }

    onFileChange2(event) {
        if (event.target.files.length > 0) {
            const file2 = event.target.files[0];
            this.form.get('file2').setValue(file2);
        }
    }

    private prepareSave(): any {
        const input = new FormData();
        input.append('file1', this.form.get('file1').value);
        console.log('File1: ', this.form.get('file1'));
        input.append('file2', this.form.get('file2').value);
        console.log('File2: ', this.form.get('file2'));

        return input;
    }

    compareDraftable() {
        console.log('Submitted Draftable');
        const formModel = this.prepareSave();
        console.log(formModel);
        this.loading = true;
        this.http.post('http://localhost:8080/demo', formModel)
            .subscribe((response: ApiResponseModel) => {
                console.log(response);
                this.comparejobdataService.url = response.url;
                this.router.navigate(['/draftable']);
            });
        setTimeout(() => {
            this.loading = false;
        }, 10000);
    }

    comparePdfDiff() {
        let fileResponse = null;
        console.log('Submitted PDF Diff');
        const formModel = this.prepareSave();
        this.loading = true;
        console.log('Formmodel: ', formModel);
        this.http.post('http://localhost:8080/pdfdiffLocal', formModel, {observe: 'response', responseType: 'arraybuffer'})
            .subscribe(response => {
                console.log('response: ', response.body);
                fileResponse = response.body;
                this.comparejobdataService.pdfFile = fileResponse;
                this.router.navigate(['/pdfcompare']);
            });
        setTimeout(() => {
            this.loading = false;
        }, 10000);
    }

    clearFile() {
        this.form.get('file1').setValue(null);
        this.form.get('file2').setValue(null);
        this.fileInput1.nativeElement.value = '';
        this.fileInput2.nativeElement.value = '';

        this.pdfSrc1 = '';
        this.pdfSrc2 = '';
        this.pdf1 = '';
        this.pdf2 = '';
    }
}
