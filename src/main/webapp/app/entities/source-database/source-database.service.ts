import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceDatabase } from 'app/shared/model/source-database.model';

type EntityResponseType = HttpResponse<ISourceDatabase>;
type EntityArrayResponseType = HttpResponse<ISourceDatabase[]>;

@Injectable({ providedIn: 'root' })
export class SourceDatabaseService {
    private resourceUrl = SERVER_API_URL + 'api/source-databases';

    constructor(private http: HttpClient) {}

    create(sourceDatabase: ISourceDatabase): Observable<EntityResponseType> {
        return this.http.post<ISourceDatabase>(this.resourceUrl, sourceDatabase, { observe: 'response' });
    }

    update(sourceDatabase: ISourceDatabase): Observable<EntityResponseType> {
        return this.http.put<ISourceDatabase>(this.resourceUrl, sourceDatabase, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISourceDatabase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceDatabase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
