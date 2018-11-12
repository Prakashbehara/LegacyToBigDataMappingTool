import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SourceDatabase } from 'app/shared/model/source-database.model';
import { SourceDatabaseService } from './source-database.service';
import { SourceDatabaseComponent } from './source-database.component';
import { SourceDatabaseDetailComponent } from './source-database-detail.component';
import { SourceDatabaseUpdateComponent } from './source-database-update.component';
import { SourceDatabaseDeletePopupComponent } from './source-database-delete-dialog.component';
import { ISourceDatabase } from 'app/shared/model/source-database.model';

@Injectable({ providedIn: 'root' })
export class SourceDatabaseResolve implements Resolve<ISourceDatabase> {
    constructor(private service: SourceDatabaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sourceDatabase: HttpResponse<SourceDatabase>) => sourceDatabase.body));
        }
        return of(new SourceDatabase());
    }
}

export const sourceDatabaseRoute: Routes = [
    {
        path: 'source-database',
        component: SourceDatabaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-database/:id/view',
        component: SourceDatabaseDetailComponent,
        resolve: {
            sourceDatabase: SourceDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-database/new',
        component: SourceDatabaseUpdateComponent,
        resolve: {
            sourceDatabase: SourceDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-database/:id/edit',
        component: SourceDatabaseUpdateComponent,
        resolve: {
            sourceDatabase: SourceDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceDatabasePopupRoute: Routes = [
    {
        path: 'source-database/:id/delete',
        component: SourceDatabaseDeletePopupComponent,
        resolve: {
            sourceDatabase: SourceDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
