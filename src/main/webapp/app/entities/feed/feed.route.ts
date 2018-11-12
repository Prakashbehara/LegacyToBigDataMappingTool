import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Feed } from 'app/shared/model/feed.model';
import { FeedService } from './feed.service';
import { FeedComponent } from './feed.component';
import { FeedDetailComponent } from './feed-detail.component';
import { FeedUpdateComponent } from './feed-update.component';
import { FeedDeletePopupComponent } from './feed-delete-dialog.component';
import { IFeed } from 'app/shared/model/feed.model';

@Injectable({ providedIn: 'root' })
export class FeedResolve implements Resolve<IFeed> {
    constructor(private service: FeedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((feed: HttpResponse<Feed>) => feed.body));
        }
        return of(new Feed());
    }
}

export const feedRoute: Routes = [
    {
        path: 'feed',
        component: FeedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Feeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feed/:id/view',
        component: FeedDetailComponent,
        resolve: {
            feed: FeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Feeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feed/new',
        component: FeedUpdateComponent,
        resolve: {
            feed: FeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Feeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feed/:id/edit',
        component: FeedUpdateComponent,
        resolve: {
            feed: FeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Feeds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const feedPopupRoute: Routes = [
    {
        path: 'feed/:id/delete',
        component: FeedDeletePopupComponent,
        resolve: {
            feed: FeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Feeds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
