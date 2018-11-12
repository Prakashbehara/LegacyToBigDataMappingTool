/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseDetailComponent } from 'app/entities/source-database/source-database-detail.component';
import { SourceDatabase } from 'app/shared/model/source-database.model';

describe('Component Tests', () => {
    describe('SourceDatabase Management Detail Component', () => {
        let comp: SourceDatabaseDetailComponent;
        let fixture: ComponentFixture<SourceDatabaseDetailComponent>;
        const route = ({ data: of({ sourceDatabase: new SourceDatabase(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SourceDatabaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceDatabaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sourceDatabase).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
