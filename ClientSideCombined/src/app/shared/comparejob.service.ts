import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {map, catchError, tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ComparejobService {
    public API = 'http://localhost:8080/';

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'multipart/form-data'
        })
    };

    constructor(private http: HttpClient) {
    }

    addDraftableCompareJob(compareJob): Observable<any> {
        return this.http.post<any>(this.API + 'draftable', compareJob, this.httpOptions).pipe(
            tap(() => console.log(`New Draftable CompareJob`)),
            catchError(this.handleError<any>('addDraftableCompareJob'))
        );
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // TODO: better job of transforming error for user consumption
            console.log(`${operation} failed: ${error.message}`);
            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }
}
